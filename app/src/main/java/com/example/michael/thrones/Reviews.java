package com.example.michael.thrones;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//needs a display method to display review results

public class Reviews extends AppCompatActivity {

    //debugger tag
    private static final String TAG = "debug";

    //creates a findLocation class object
    FindLocation findLocation = null;
    //creates a location
    protected Location location = null;

    //creates an arrayList to get the list of reviews from the cloud
    ArrayList<ParseObject> reviews = new ArrayList<ParseObject>(50);

    //creates an object for the listVIew
    LinearLayout myListLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        //sets the screen oritentation to vertical
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //sets the findLocation object
        findLocation = new FindLocation(getApplicationContext());
        //sets the location object
        location = findLocation.getLocation();

        //gets the list from the reviewsHandler class
        //reviews = reviewsHandler.getReviewsList();

        //layout
        myListLayout = (LinearLayout)findViewById(R.id.scrollLinear);

        Map<String, Object> parameters = new HashMap<String, Object>();
        //puts in the parameters I want
        parameters.put("userLat", location.getLatitude());
        parameters.put("userLng", location.getLongitude());
        //calls the parse cloud function
        ParseCloud.callFunctionInBackground("getReviews", parameters, new FunctionCallback<Map<String, Object>>() {
            public void done(Map<String, Object> mapObject, ParseException e) {
                if (e == null) {
                    for(int i = 0; i < mapObject.size(); i++)
                    {
                        Log.i(TAG, " _ "+mapObject.get(i));
                    }
                } else {
                    System.out.println("didn't work.  Parse Exception:\n" + e);
                }
            }
        });

        //calls the display method to display the reviews
        if(reviews == null) {
            //displayMessage();
        }else{
            //display();
        }
    }

    //diplays results from the reviews in the database
    public void display()
    {
        for(int i = 0; i < reviews.size(); i++)
        {
            System.out.print(reviews.get(i));
        }
    }

    //displays if there are no reviews in the area
    public void displayMessage()
    {
        //creates a new textView
        TextView noReviews = new TextView(this);
        //sets the text
        noReviews.setText("There are no reviews in a 25 mile radius of you.  Be the first in your area!");
        //sets the textColor
        noReviews.setTextColor(Color.WHITE);
        //sets the TextSize
        noReviews.setTextSize(20);
        //sets the background
        noReviews.setBackgroundColor(Color.parseColor("#FF8300"));

        //adds textView to the layout
        myListLayout.addView(noReviews);
    }

}
