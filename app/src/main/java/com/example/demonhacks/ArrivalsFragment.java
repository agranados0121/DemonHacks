package com.example.demonhacks;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ArrivalsFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    private static final int CODE_FOR_SEARCH_ACTIVITY = 111;
    private static final String TAG = "ArrivalsActivity";
    private ArrayList<Route> routeList = new ArrayList<>();
    private ArrayList<Station> stationList = new ArrayList<>();
    private String requestedStation;
    private RecyclerView recyclerView;
    private RouteAdapter routeAdapter;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // retain this fragment across activity reconfigs
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.activity_arrivals, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        View view = getView();

        //TODO init station list

        Intent intent = getActivity().getIntent();
        Station station;
        if (intent.hasExtra("STATION")) {
            station = (Station) intent.getSerializableExtra("STATION");
            requestedStation = station.getMapId();
        } else {
            requestedStation = "40380"; // Default 41220
        }

        recyclerView = view.findViewById(R.id.arrivalsRecycler);
        routeAdapter = new RouteAdapter(routeList, this);
        recyclerView.setAdapter(routeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        routeList.clear();
        routeAdapter.notifyDataSetChanged();

        getData();

        recyclerView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildLayoutPosition(view);
                Route selection = routeList.get(position);
                Intent intent = new Intent(getContext(), TrainMapActivity.class);
                intent.putExtra("ROUTE", selection);
                startActivity(intent);
            }
        });
    }

    public void getData() {
        Log.d(TAG, "getData: Parsing JSON");
        try {
            String url = String.format("http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx?key=73436616b5af4465bc65790aa9d4886c&mapid=" + requestedStation + "&=40530&outputType=JSON");
            new JsonParser(routeList, requestedStation, routeAdapter).execute(url);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getData: ERROR PARSING JSON DATA");
        }
    }

    public void openTrainMapActivity(int position) {
        Route selection = routeList.get(position);
        Intent intent = new Intent(getContext(), TrainMapActivity.class);
        intent.putExtra("ROUTE", selection);
        startActivity(intent);
    }

    public void openSearchActivity() {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        //intent.putParcelableArrayListExtra(stationList);
        startActivityForResult(intent, CODE_FOR_SEARCH_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_FOR_SEARCH_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                try {
                    Station station = (Station) data.getSerializableExtra("STATION");
                    routeAdapter.clear();
                    Log.d(TAG, "onActivityResult: routeList " + routeList.size());
                    requestedStation = station.getMapId();
                    getData();
                } catch (Exception e) {
                    Log.d(TAG, "onActivityResult: Error parsing result");
                    e.printStackTrace();
                }

            } else {
                Log.d(TAG, "onActivityResult: Result Code:" + requestCode);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        openSearchActivity();
    }

    @Override
    public boolean onLongClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        openTrainMapActivity(position);
        return true;
    }
}
