package com.example.michael.thrones;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseObject;
import java.util.Locale;

public class WriteRview extends AppCompatActivity{

    //Log String for debugging
    private final String TAG = "works";

    //location textview reference
    private TextView locationTextView = null;
    //Ratingbar reference to get the star rating
    private RatingBar starRatingRatingBar = null;
    //edittext reference
    private EditText comments = null;

    //double to hold user star rating
    double userStarRating = 0;
    //holds where the user is in the song
    private double length = 0;

    //Creates radioGroup reference
    private RadioGroup maleFemaleRadioGroup = null;
    private RadioGroup cleanlinessRadioGroup = null;
    private RadioGroup equippedRadioGroup = null;

    //these are the types for the answers
    private String gender = "";
    private String equipment = "";
    private String cleanliness = "";
    private String description = "";
    //creates a parseObject to store all the data from
    //the questionnaire
    ParseObject questionnaireAnswers = new ParseObject("QuestionnaireAnswers");

    //creates a mediaplayer for the background music
    MediaPlayer elevatorMusic = null;

    //holds the user location
    private Location location = null;
    //creates a FindLocation object
    FindLocation findLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the screen oritentation to vertical
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //sets the screen view to the layout
        setContentView(R.layout.activity_write_rview);

        findLocation = new FindLocation(this.getApplicationContext());

        //sets the elevator music to the correct file
        elevatorMusic = MediaPlayer.create(this, R.raw.elevator_music);
        //calls the method to start the music
        startMusic();

        //this connects the xml and textView reference
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        //references the editText in xml
        comments = (EditText) findViewById(R.id.comments);
        //connects the xml and the editTextView reference
        starRatingRatingBar = (RatingBar) findViewById(R.id.starRating);

        //connects the radiogroup reference to the xml
        maleFemaleRadioGroup = (RadioGroup) findViewById(R.id.question1RadioGroup);
        cleanlinessRadioGroup = (RadioGroup)findViewById(R.id.cleanRadioGroup);
        equippedRadioGroup = (RadioGroup) findViewById(R.id.equipRadioGroup);

        //gets the location of the user
        location = findLocation.getLocation();
        //log message for me to see location object
        Log.i(TAG, "GeoLocation is : "+location);
        //gets the complete address if the location is not null
        if(location != null)
        {
            Log.i(TAG, "there is a valid location");
            //sets the location textView
            setLocationTextView();
        }
    }

    //starts the elevator music
    public void startMusic()
    {
        //starts the elevator music
        elevatorMusic.start();
        //keeps the music looping through
        elevatorMusic.setLooping(true);
        elevatorMusic.setVolume(100, 100);
    }

    //restarts the music
    @Override
    public void onResume()
    {
        super.onResume();
        elevatorMusic.seekTo((int)length);
        elevatorMusic.start();
    }


    @Override
    public void onPause(){
        super.onPause();
        //pauses and releases the elevator music
        elevatorMusic.pause();
        length = elevatorMusic.getCurrentPosition();

    }

    //backs up the survey anwsers to the parse.com
    public void submitSurvey(View v)
    {
        //gets the survey anwsers
        getAnwsers();

        //puts all the info into parse.com
        questionnaireAnswers.put("clean", cleanliness);
        questionnaireAnswers.put("equip", equipment);
        questionnaireAnswers.put("gen", gender);
        questionnaireAnswers.put("rate", userStarRating);
        questionnaireAnswers.put("desc", description);
        questionnaireAnswers.put("lat", location.getLatitude());
        questionnaireAnswers.put("lng", location.getLongitude());
        questionnaireAnswers.saveInBackground();

        //creates an intent to go back to the maps activity
        Intent backToMainIntent = new Intent(this, MainActivity.class);
        //goes back to the maps activity
        startActivity(backToMainIntent);

        //creates a toast to tell the user their info has been entered
        Toast.makeText(getApplicationContext(), "Thank you for your input!", Toast.LENGTH_SHORT).show();
    }

    public void getAnwsers()
    {
        //getting the number of stars
        if(starRatingRatingBar != null) {
            userStarRating = starRatingRatingBar.getRating();
        }

        //gets the comments in the editText and puts them into a string
        description = comments.getText().toString();

        //switch statement to get which radiobutton was checked
        switch(maleFemaleRadioGroup.getCheckedRadioButtonId())
        {
            case R.id.maleRadButton:
                gender = "male";
                break;
            case R.id.femaleRadButton:
                gender = "female";
                break;
            default:
                gender = "unkown";
                break;
        }

        //switch to get how clean bathroom is
        switch(cleanlinessRadioGroup.getCheckedRadioButtonId())
        {
            case R.id.veryClean:
                cleanliness = "Very Clean!";
                break;
            case R.id.clean:
                cleanliness = "Clean";
                break;
            case R.id.okClean:
                cleanliness = "Okay";
                break;
            case R.id.notClean:
                cleanliness = "Not Clean";
                break;
            case R.id.dirtyClean:
                cleanliness = "Dirty";
                break;
            default:
                cleanliness = "No User Answer";
                break;
        }

        //switch to get how equipped bathroom is
        switch(equippedRadioGroup.getCheckedRadioButtonId())
        {
            case R.id.veryEquip:
                equipment = "Well Equipped!";
                break;
            case R.id.equip:
                equipment = "Equipped";
                break;
            case R.id.okEquip:
                equipment = "Okay";
                break;
            case R.id.notEquip:
                equipment = "Not Equipped";
                break;
            case R.id.dirtyEquip:
                equipment = "Help I Need Toilet Paper!";
                break;
            default:
                equipment = "No User Answer";
                break;
        }
    }

    //sets the locationTextViews location
    public void setLocationTextView()
    {
        Log.i(TAG, "setLocationTextView called");
        //sets the textView
        locationTextView.setText("Lat : " + location.getLatitude() + "\nLng : " + location.getLongitude());
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
