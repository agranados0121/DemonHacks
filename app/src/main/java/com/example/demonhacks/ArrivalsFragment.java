package com.example.demonhacks;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import demon.CTAParser;

import static android.app.Activity.RESULT_OK;

public class ArrivalsFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    private static final int CODE_FOR_SEARCH_ACTIVITY = 123;
    private static final String TAG = "ArrivalsActivity";
    private ArrayList<Route> routeList = new ArrayList<>();
    private ArrayList<Station> stationList = new ArrayList<>();
    private ArrayList<Station> requestedStations = new ArrayList<>();
    private double[] locationCoordinates = new double[2];
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

        routeList.clear();
        requestedStations.clear();
        getStationData();

        Intent intent = getActivity().getIntent();
        Station station;

        if (intent.hasExtra("STATION")) {
            station = (Station) intent.getSerializableExtra("STATION");
            requestedStations.add(station);
        } else {
            getNearbyStations();
            if (requestedStations.isEmpty()) {
                requestedStations.add(new Station("40380", "Clark/Lake", "", ""));
            }
        }

        recyclerView = view.findViewById(R.id.arrivalsRecycler);
        routeAdapter = new RouteAdapter(routeList, this);
        recyclerView.setAdapter(routeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

    public void doRefresh() {
        getActivity().finish();
        startActivity(getActivity().getIntent());
    }

    private void getData() {
        Log.d(TAG, "getData: Parsing JSON");
        try {
            Log.d(TAG, "getData: " + requestedStations.size());
            ArrayList<String> URLS = new ArrayList<>();
            for (Station request: requestedStations) {
                String mapId = request.getMapId();
                String url = "http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx?" +
                        "key=73436616b5af4465bc65790aa9d4886c&" +
                        "mapid=" + mapId + "&=40530&outputType=JSON";
                URLS.add(url);
            }
            new JsonParser(routeList, URLS, routeAdapter).execute();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getData: ERROR PARSING JSON DATA");
        }
    }

    private void getStationData() {
        try {
            stationList.clear();
            BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity()
                    .getAssets()
                    .open("CTA_-_System_Information_-_List_of__L__Stops.csv")));
            CTAParser data = new CTAParser(reader);
            for (int i = 0; i < data.Map_ID.length; i++) {
                Station s = new Station(data.Map_ID[i], data.Station_Name[i], data.Location_X[i], data.Location_Y[i]);
                if (!stationList.contains(s)) {
                    stationList.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getNearbyStations() {
        try {
            //TODO Request Location Permission, currently must be done manually through settings
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            locationCoordinates[0] = latitude; locationCoordinates[1] = longitude;

            Log.d(TAG, "stationInRange: User Long / Lat: " + longitude + " / " + latitude);

            for (Station station: stationList) {
                double lon = Double.parseDouble(station.getLon());
                double lat = Double.parseDouble(station.getLat());
                double distance = distanceBetweenCoords(latitude, lat, longitude, lon);
                if (distance <= 800) { // Approx 0.5 miles, figure out
                    if (!requestedStations.contains(station)) {
                        requestedStations.add(station);
                    }
                }
                Log.d(TAG, "stationInRange: No station found");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Haversine Formula, returns distance in meters
     * Credit: David George
     * https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude */
    private double distanceBetweenCoords(double lat1, double lat2, double lon1, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = 6371 * c * 1000;
        Log.d(TAG, "distance: " + distance);
        return distance;
    }

    private void openTrainMapActivity(int position) {
        Route selection = routeList.get(position);
        Intent intent = new Intent(getContext(), TrainMapActivity.class);
        intent.putExtra("ROUTE", selection);
        intent.putExtra("LOCATION", locationCoordinates);
        startActivity(intent);
    }

    private void openSearchActivity() {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtra("STATIONS", stationList);
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
                    requestedStations.clear();
                    Log.d(TAG, "onActivityResult: routeList " + routeList.size());
                    requestedStations.add(station);
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
        // TODO: Implement search via menu option, have short clicks redirect to map
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
