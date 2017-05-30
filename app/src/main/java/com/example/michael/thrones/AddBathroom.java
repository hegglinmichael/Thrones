package com.example.michael.thrones;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class AddBathroom extends AppCompatActivity {

    //creates a FindLocation object
    FindLocation findLocation = null;
    //declares a location
    Location userLocation = null;
    //declares a textView
    TextView location = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bathroom);
        //sets the screen oritentation to vertical
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        findLocation = new FindLocation(getApplicationContext());
        //references the textView in the R.java
        location = (TextView)findViewById(R.id.location);
        //gets the location of the user
        userLocation = findLocation.getLocation();
        //gets the complete address if the location is not null
        if(userLocation != null)
        {
            //sets the textView to the user's address
            setLocationTextView();
        }
    }

    //sets the textView
    public void setLocationTextView()
    {
        location.setText("Lat : "+userLocation.getLatitude() + "\nLng : "+userLocation.getLongitude());
    }

    //takes you to the servey after button is pressed
    public void submitPlace(View v)
    {
        //creates an intent to go back to the maps activity
        Intent writeReviewIntent = new Intent(this, WriteRview.class);
        //goes back to the maps activity
        startActivity(writeReviewIntent);
    }
}
