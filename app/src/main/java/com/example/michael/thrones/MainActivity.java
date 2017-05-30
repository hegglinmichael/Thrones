package com.example.michael.thrones;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //editText that cnotains address from the user
    EditText userAddressEnteredET = null;
    //string that holds the text the user entered
    String userAddressEntered = "";

    //holds the code for the request
    private final int REQUEST_FINE_ACCESS_CODE = 42562;

    //creates a coder string to hold extra in the search location maps intent
    protected static final String SEARCH = "MainActivity.thrones.search";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the screen oritentation to vertical
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        //editText for address user want to go to
        userAddressEnteredET = (EditText)findViewById(R.id.enterAnAddress);

        //checks to see if the manifest has the locaiton permissions
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request fine location permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_ACCESS_CODE);
        }
    }

    //method to open reviews if Reviews
    public void openReviews(View v)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //creates and intent
            Intent reviewIntent = new Intent(this, Reviews.class);
            //starts the activity
            startActivity(reviewIntent);
        } else {
            //tells the user it will not work because they did not accept location permissions
            Toast.makeText(this, "You did not okay the location permissions so this part of the application can't run!", Toast.LENGTH_SHORT).show();
        }
    }

    //FindBathroom is clicked   ---------------------------------------
    public void getRatedBathrooms(View v)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //creates an intent and launches it
            Intent findBathroomReviews = new Intent(this, FindBathroomReviews.class);
            startActivity(findBathroomReviews);
        } else {
            //tells the user it will not work because they did not accept location permissions
            Toast.makeText(this, "You did not okay the location permissions so this part of the application can't run!", Toast.LENGTH_SHORT).show();
        }
    }

    //opens the write reviews activity
    public void openWriteReview(View v)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //creates an intent
            Intent writeReviewsIntent = new Intent(this, WriteRview.class);
            //starts the activity
            startActivity(writeReviewsIntent);
        } else {
            //tells the user it will not work because they did not accept location permissions
            Toast.makeText(this, "You did not okay the location permissions so this part of the application can't run!", Toast.LENGTH_SHORT).show();
        }
    }

    //opens the add a bathroom activity
    public void openAddBathroom(View v)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //creates an intent
            Intent addBathroomIntent = new Intent(this, AddBathroom.class);
            //starts the activity
            startActivity(addBathroomIntent);
        } else {
            //tells the user it will not work because they did not accept location permissions
            Toast.makeText(this, "You did not okay the location permissions so this part of the application can't run!", Toast.LENGTH_SHORT).show();
        }
    }

    //google maps the address or location the user looks up
    public void directionsToLocation(View v)
    {
        //CODE BELOW USES THE GOOGLE MAPS IN THE THRONES APPLICATION

        //launches the google maps application
        Intent mapsIntent = new Intent(this, MapsActivity.class);
        //gets the content from the edittext
        userAddressEntered = userAddressEnteredET.getText().toString();
        //adds an extra into the intent
        mapsIntent.putExtra(SEARCH, userAddressEntered);
        if(!userAddressEntered.equals("") || userAddressEntered == null)
        {
            //starts the maps activity with directions to the location
            startActivity(mapsIntent);
            //clears the editText
            userAddressEnteredET.setText("");
        }else{
            Toast.makeText(this, "No address entered!", Toast.LENGTH_SHORT).show();
        }


        //CODE BELOW USES GOOGLE MAPS APPLICATION ON THE USER'S PHONE
        /*
        //needs to get the editText then open google maps with that info in it
        userAddressEntered = "google.navigation:q=a+"+userAddressEnteredET.getText().toString()+"&mode=d";

        //creates a uri from the string above
        Uri gmaIntentUri = Uri.parse(userAddressEntered);
        //creates a map intent
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmaIntentUri);
        //sets the package to the google maps intent
        mapIntent.setPackage("com.google.android.apps.maps");
        //statement test if the device can handle the google maps intent
        if((mapIntent).resolveActivity(getPackageManager()) != null)
        {
            //starts the map activity
            startActivity(mapIntent);
        }

        //clears the text and resets the hint
        userAddressEnteredET.setText("");
        userAddressEnteredET.setHint("Enter an address");*/
    }

    //this takes care of closing the soft
    //keyboard when stuff is pressed
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
}
