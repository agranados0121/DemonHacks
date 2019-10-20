package com.example.demonhacks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ArrivalsFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{
    private static final String TAG = "ArrivalsActivity";
    private ArrayList<Route> routeList = new ArrayList<>();
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

        requestedStation = "41220"; // 41220 == Fullerton station stop, 40380 == Clark/Lake

        recyclerView = view.findViewById(R.id.arrivalsRecycler);
        routeAdapter = new RouteAdapter(routeList, this);
        recyclerView.setAdapter(routeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        routeList.clear();
        routeAdapter.notifyDataSetChanged();

        new JsonParser(routeList, requestedStation, routeAdapter).execute(getString(R.string.cta_api_url));

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

    public void openTrainMapActivity(int position) {
        Route selection = routeList.get(position);
        Intent intent = new Intent(getContext(), TrainMapActivity.class);
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
        return false;
    }
}
