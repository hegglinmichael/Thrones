package com.example.michael.thrones;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//need a method so a place only comes up once (Should it return an array list of addresses)
//need a method that puts reviews on the screen
//needs to look for restaurants if there are less than a certain amount of ratings in the area
//need to go to restaurants if there is not enough data

public class FindBathroomReviews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bathroom_reviews);

    }

    //will sort the arraylist of reviews by starRating
    public void sort()
    {

    }

    //will remove duplicate addresses after the arraylist gets average star rating
    public void removeDuplicates()
    {

    }

    //will diplay the TextViews on the screen
    public void display()
    {

    }
}
