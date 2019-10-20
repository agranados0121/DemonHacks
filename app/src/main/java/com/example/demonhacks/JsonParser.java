package com.example.demonhacks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/*
 * Fetches recent train data from CTA's API
 *
 * Implemented JSON Parsing following aadigurung's solution:
 * https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
 *
 */
public class JsonParser extends AsyncTask<String, String, String> {

    private ArrayList<Route> routeList;
    private String requestedStation;
    private RouteAdapter routeAdapter;
    private static final String TAG = "JsonParser";

    public JsonParser(ArrayList<Route> routeList, String requestedStation, RouteAdapter routeAdapter) {
        this.routeList = routeList;
        this.requestedStation = requestedStation;
        this.routeAdapter = routeAdapter;
    }

    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = reader.readLine()) != null) {
                stringBuilder.append(receiveString);
                Log.d(TAG, "doInBackground > " + receiveString);
            }

            return stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // TODO Load Data into Recycler View

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObject2 = jsonObject.getJSONObject("ctatt");

            JSONArray jsonArray = jsonObject2.getJSONArray("eta");
            int length = jsonArray.length();

            Log.d(TAG, String.format("doRead: Reading Data for %s trains", length));
            routeList.clear();

            for (int i = 0; i < length; i++) {
                JSONObject trainJsonObj = jsonArray.getJSONObject(i);

                String arrivalTime = trainJsonObj.getString("arrT");
                Log.d(TAG, "onPostExecute: "+ arrivalTime);

                String[] timeData = getTime(arrivalTime);

                String latitude = trainJsonObj.getString("lat");
                String longitude = trainJsonObj.getString("lon");

                //TODO: Fix Time String formatting
                Train train = new Train(timeData[0], timeData[1], latitude, longitude);

                String color = trainJsonObj.getString("rt");
                String stationId = trainJsonObj.getString("staId");
                String stationName = trainJsonObj.getString("staNm");
                String destination = trainJsonObj.getString("destNm");

                ArrayList<Train> list = new ArrayList<>();
                list.add(train);

                Route route = new Route(color, stationId, stationName, destination, list);

                if (route.getStationId().equals(requestedStation) && !route.getDestination().equals("See train")) {
                    int index = routeCellIndex(route);
                    if (index > -1) {
                        routeList.get(index).getTrains().add(train);
                    } else {
                        routeList.add(route);
                    }
                }
            }
            Collections.sort(routeList, new SortByLine());
            routeAdapter.notifyDataSetChanged();
            for (Route r: routeList) {
                Log.d(TAG, "onPostExecute: " + r.getLine());
                for (int j = 0; j < r.getTrains().size(); j++) {
                    Log.d(TAG, String.format("onPostExecute: %s %s %s", r.getStationId(), r.getLine(), r.getTrains().get(j).getArrivalTime()));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int routeCellIndex(Route route) {
        for (int i = 0; i < routeList.size(); i++) {
            if (routeList.get(i).sharesRoute(route)) {
                return i;
            }
        }
        return -1;
    }

    // Arturo's Time Parsing Method
    private static String[] getTime(String arrivalTime) {
        String arrival_hour = arrivalTime.substring(11,13);
        String arrival_min = arrivalTime.substring(14,16);
        String[] s = new String[2];
        LocalTime tm = java.time.LocalTime.now();
        int hr = Integer.parseInt(arrival_hour);
        int min = Integer.parseInt(arrival_min);

        if(hr == 0) {
            s[0] = String.format("Arriving at %d:%s AM", (Integer.parseInt(arrival_hour)+12), arrival_min);
            s[1] = String.format("%d min", (Integer.parseInt(arrival_min)- tm.getMinute()));
        }
        else if(hr == 12) {
            s[0] = String.format("Arriving at %d:%s PM", (Integer.parseInt(arrival_hour)-12), arrival_min);
            s[1] = String.format("%s min", arrival_min);
        }
        else if(hr > 0 && hr < 12) {
            int min_remaining = java.lang.Math.abs(min - tm.getMinute());
            s[0] = String.format("Arriving at %s:%s AM", (Integer.parseInt(arrival_hour)), arrival_min);
            s[1] = String.format("%d min" , min_remaining);

        }
        else {// hr > 12 && hr < 0
            int min_remaining = java.lang.Math.abs(tm.getMinute()-min);
            s[0] = String.format("Arriving at %s:%s PM", (Integer.parseInt(arrival_hour)-12), arrival_min);
            s[1] = String.format("%d min" , min_remaining);
        }
        return s;
    }
}