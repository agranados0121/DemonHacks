package com.example.demonhacks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private static final String TAG = "MainActivity";

    private ArrayList<Route> routeList = new ArrayList<>();
    private String requestedStation;

    private RecyclerView recyclerView;
    private RouteAdapter routeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Do based on user location to nearest station OR/AND on manual selection from search activity
        requestedStation = "41220"; // 41220 == Fullerton station stop

        recyclerView = findViewById(R.id.arrivalsRecycler);
        routeAdapter = new RouteAdapter(routeList, this);
        recyclerView.setAdapter(routeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new JsonParser(routeList, requestedStation, routeAdapter).execute(getString(R.string.cta_api_url));

    }

    public void openTrainMapActivity(int position) {
        Route selection = routeList.get(position);
        Intent intent = new Intent(this, TrainMapActivity.class);
        intent.putExtra("ROUTE", selection);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        openTrainMapActivity(position);
    }

    @Override
    public boolean onLongClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        openTrainMapActivity(position);
        return true;
    }
}