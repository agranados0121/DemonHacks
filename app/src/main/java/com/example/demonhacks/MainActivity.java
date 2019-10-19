package com.example.demonhacks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

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

    @Override
    public void onClick(View v) {
        // TODO: SWITCH TO MAP TO VIEW TRAINS
    }

    @Override
    public boolean onLongClick(View v) {
        // TODO: SWITCH TO MAP TO VIEW TRAINS
        return true;
    }
}