package com.example.michael.thrones;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Michael on 10/18/2015.
 */
public class FindLocation {

    //debugger string used for debugging
    private static final String TAG = "debug";

    //creates a location manager
    protected LocationManager locationManager = null;
    //creates a location object
    protected Location location = null;

    //creates a context for the class that is calling it
    private Context context = null;

    //constructor for the findLocation class
    public FindLocation(Context context) {
        this.context = context;
    }

    //gets the user's location
    public Location getLocation() {
        //gets the location sevices
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //tells me what the locationManager object holds
        Log.i(TAG, "Location Manager Object Holds : " + locationManager);
        //gets the location from the location manager's location service
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //tells the user it will not work because they did not accept location permissions
            Toast.makeText(context, "You did not okay the location permissions so this part of the application can't run!", Toast.LENGTH_SHORT).show();
        } else  {
            //gets the location from the locationManager's gps_provider
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.i(TAG, "Location is : "+location);
        }

        //returns the location
        return location;
    }
}
