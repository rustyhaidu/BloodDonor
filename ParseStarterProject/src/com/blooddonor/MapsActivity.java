package com.blooddonor;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.starter.R;

import java.util.List;

/**
 * Created by Claudiu on 8/22/2015.
 */
public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
       // Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //double longitude = location.getLongitude();
        //double latitude = location.getLatitude();

        mMap.setMyLocationEnabled(true);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Marker"));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("BloodCenter");
        query.whereExists("Location");
        query.whereExists("Name");

        query.findInBackground(new FindCallback<ParseObject>() {
            //String Addresses = "";
            //String Point = "";
            ParseGeoPoint userLocation;

            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {


                if (e == null) {
                    Log.d("score", "Retrieved ");

                    for (int i = 0; i < list.size(); i++) {
                        ParseObject p = list.get(i);

                        userLocation = list.get(i).getParseGeoPoint("Location");


                        double geo1Int = userLocation.getLatitude();
                        double geo2Int = userLocation.getLongitude();

                        //Point = Point + String.valueOf(geo1Int) + " " + String.valueOf(geo2Int) + ";";
                        // Addresses = Addresses + p.getString("Location")+";";

                        mMap.addMarker(new MarkerOptions().position(new LatLng(geo1Int, geo2Int)).title(p.getString("Name")));

                    }
                    //getCenters(Point);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }
}