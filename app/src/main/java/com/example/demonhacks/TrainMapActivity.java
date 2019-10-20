package com.example.demonhacks;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TrainMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Route route;
    private static final String TAG = "TrainMapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        if (intent.hasExtra("ROUTE")) {
            route = (Route) intent.getSerializableExtra("ROUTE");
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng oof = new LatLng(41.879507, -87.626037);
        mMap.addCircle(new CircleOptions().center(oof).radius(250).fillColor(Color.CYAN).strokeColor(Color.CYAN));
        ArrayList<LatLng> markers = new ArrayList<>();
            for (Train t : route.getTrains()) {
                try {
                    Double lat = Double.parseDouble(t.getLatitude());
                    Double lon = Double.parseDouble(t.getLongitude());
                    LatLng coordinates = new LatLng(lat, lon);
                    markers.add(coordinates);
                    String title = String.format("%s line to %s", route.getLine(), route.getDestination());
                    mMap.addMarker(new MarkerOptions().position(coordinates).title(title));
                } catch (NumberFormatException e) {
                    Log.d(TAG, "onMapReady: Error Parsing Coordinates");
                    e.printStackTrace();
                }
            }
        LatLng chicagoCoordinates = new LatLng(41.8781, -87.6298);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chicagoCoordinates, 11));
    }
}
