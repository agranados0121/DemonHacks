package com.example.demonhacks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private ArrayList<Station> stationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StationAdapter stationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.searchRecycler);
        stationAdapter = new StationAdapter(stationList, this);
        recyclerView.setAdapter(stationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();
    }

    private void getData() {
//        CTAParser data = new CTAParser();
//        for (int i = 0; i < data.Map_ID.length; i++){
//            stationList.add(new Station(data.Map_ID[i], data.Station_Name[i], data.Location_X[i], data.Location_Y[i]));
//        }
        stationList.add(new Station("41220", "Fullerton","",""));
        stationList.add(new Station("41320", "Belmont","41.939751","-87.65338"));
        stationList.add(new Station("40380", "Clark/Lake","",""));
        stationList.add(new Station("40360", "Southport","",""));
        stationList.add(new Station("40680", "Adams/Wabash","",""));
    }

    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        Intent data = new Intent(this, MainActivity.class);
        data.putExtra("STATION", stationList.get(position));
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}