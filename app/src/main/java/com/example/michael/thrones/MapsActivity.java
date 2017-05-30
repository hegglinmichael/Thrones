package com.example.michael.thrones;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "aa";

    //google maps object
    private static GoogleMap mMap = null;
    //makes a drawpolyline object
    private static DrawPolyline drawPolyline = null;

    //holds the user location
    private Location userLocation = null;
    //holds the user latlng and destination latlng
    private LatLng userLatLng = null;
    private LatLng destinationLatLng = null;

    //creates an intent to recieve the place the user searched
    Intent getIntentInfo = null;
    //creates a variable to hold the place the user searched
    String searchedLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //creates a FindLocation object to get the user's location
        FindLocation findLocation = new FindLocation(getApplicationContext());
        //sets the userLocation variable to the user's location
        userLocation = findLocation.getLocation();
        //sets up the user LatLng
        userLatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());

        //gets the intent used to call this activity
        getIntentInfo = getIntent();
        //sets the string equal to the searched location inside the intent
        searchedLocation = getIntentInfo.getStringExtra(MainActivity.SEARCH);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        //gets the destinations latlng
        destinationLatLng = addressToLngLat(searchedLocation);
        Log.i(TAG, "DestinationLatLng : "+destinationLatLng);

        if(mMap != null)
        {
            //moves the camera to the user location
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15));
            //puts a marker on the destinations location
            mMap.addMarker(new MarkerOptions().position(destinationLatLng).title("Destination")).showInfoWindow();

            drawPolyline = new DrawPolyline(userLatLng, destinationLatLng);
            Log.i(TAG, "Polyline object created");
            //gets the url to send to the directions api
            String url = drawPolyline.createUrl();
            //downloads the url data
            try {
                drawPolyline.downloadUrl(url);
                Log.i(TAG, "downloaded json from url");
            } catch (IOException e) {
                Log.i(TAG, "could not download url : "+e.toString());
            }
        }else{
            Log.i(TAG, "mMap is null!");
        }
    }

    public void finishDrawingPolyline() throws JSONException {
        //EXECUTES BEFORE THE DATA IS FINISHED DOWNLOADING AND THE VARIABLES ARE SET
        String data = drawPolyline.getJSONData();
        Log.i(TAG, "json file downloaded and received.  Data received : " + data);

        //gets the polyline options
        PolylineOptions polylineOptions = drawPolyline.getPolylineOptions(data);
        Log.i(TAG, "PolyLineOptions are : " + polylineOptions.toString());

        //draws it on the map
        mMap.addPolyline(polylineOptions);
        Log.i(TAG, "polLineOptions added");
    }

    //turns an address into a LatLng
    public LatLng addressToLngLat(String address) {
        LatLng addressLatLng = null;
        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(address, 1);
        } catch (IOException e) {
            Log.i(TAG, "could not get location from name : "+e.toString());
        }
        if(addresses.size() > 0) {
            double latitude= addresses.get(0).getLatitude();
            double longitude= addresses.get(0).getLongitude();
            addressLatLng = new LatLng(latitude, longitude);
        }

        return addressLatLng;
    }
}
