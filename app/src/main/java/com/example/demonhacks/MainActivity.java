package com.example.demonhacks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<Route> routeList = new ArrayList<>();
    private String requestedStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Do based on user location to nearest station OR/AND on manual selection from search activity
        requestedStation = "41220"; // 41220 == Fullerton station stop

        // Loads Data into trainData
        routeList.clear();
        new JsonParser(routeList, requestedStation).execute(getString(R.string.cta_api_url));
    }
}