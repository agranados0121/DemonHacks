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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/*
 * Fetches recent train data from CTA's API
 *
 * Implemented JSON Parsing following aadigurung's solution:
 * https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
 *
 */
public class JsonParser extends AsyncTask<String, String, ArrayList<String>> {

    private ArrayList<Route> routeList;
    private ArrayList<String> URLS;
    private RouteAdapter routeAdapter;
    private static final String TAG = "JsonParser";

    public JsonParser(ArrayList<Route> routeList, ArrayList<String> URLS, RouteAdapter routeAdapter) {
        this.routeList = routeList;
        this.URLS = URLS;
        this.routeAdapter = routeAdapter;
    }

    protected ArrayList<String> doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            ArrayList<String> results = new ArrayList<>();
            for (String requestURL: URLS) {
                URL url = new URL(requestURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                StringBuilder stringBuilder = new StringBuilder();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                String receiveString = "";
                while ((receiveString = reader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                    Log.d(TAG, "doInBackground: " + receiveString.length());
                }
                results.add(stringBuilder.toString());
            }

            return results;

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
    protected void onPostExecute(ArrayList<String> results) {
        super.onPostExecute(results);

        for(String result: results) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject jsonObject2 = jsonObject.getJSONObject("ctatt");

                JSONArray jsonArray = jsonObject2.getJSONArray("eta");
                int length = jsonArray.length();

                Log.d(TAG, String.format("onPostExecute: Reading Data for %s trains", length));

                for (int i = 0; i < length; i++) {
                    JSONObject trainJsonObj = jsonArray.getJSONObject(i);

                    String arrivalTime = trainJsonObj.getString("arrT");
                    Log.d(TAG, "onPostExecute: " + arrivalTime);

                    String[] timeData = parseArrivalTime(arrivalTime);

                    String latitude = trainJsonObj.getString("lat");
                    String longitude = trainJsonObj.getString("lon");

                    Train train = new Train(timeData[0], timeData[1], latitude, longitude);

                    String color = trainJsonObj.getString("rt");
                    String stationId = trainJsonObj.getString("staId");
                    String stationName = trainJsonObj.getString("staNm");
                    String destination = trainJsonObj.getString("destNm");

                    ArrayList<Train> list = new ArrayList<>();
                    list.add(train);

                    Route route = new Route(color, stationId, stationName, destination, list);

                    if (!route.getDestination().equals("See train")) {
                        int index = routeCellIndex(route);
                        if (index > -1) {
                            if (routeList.get(index).getTrains().size() < 4) {
                                routeList.get(index).getTrains().add(train);
                            }
                        } else {
                            routeList.add(route);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.sort(routeList, new SortByLine());
        routeAdapter.notifyDataSetChanged();
    }

    public int routeCellIndex(Route route) {
        for (int i = 0; i < routeList.size(); i++) {
            if (routeList.get(i).sharesRoute(route)) {
                return i;
            }
        }
        return -1;
    }

    private String currentTime() {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return formatter.format(date);
    }

    // TODO: Works but is there a cleaner way?
    private String[] parseArrivalTime(String arrivalTime) {
        String[] result = {"", ""};
        try {
            arrivalTime = arrivalTime.substring(11);

            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date current = format.parse(currentTime());
            Date arrival = format.parse(arrivalTime);
            long difference = (arrival.getTime() - current.getTime()) / 60000;

            Calendar c = Calendar.getInstance();
            c.setTime(arrival);
            String meridiem = (c.get(Calendar.HOUR_OF_DAY) >= 12) ? "pm" : "am";

            String minutes = String.valueOf(c.get(Calendar.MINUTE));
            minutes = (minutes.length() == 2) ? minutes : "0" + minutes;

            String timestamp = String.format("%s:%s", c.get(Calendar.HOUR), minutes);

            result[0] = String.format("Arriving at %s %s", timestamp, meridiem);
            result[1] = (difference <= 1) ? "Due" : difference + " min";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}