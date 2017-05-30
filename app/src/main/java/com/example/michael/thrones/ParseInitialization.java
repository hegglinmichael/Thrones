package com.example.michael.thrones;

//this class initializes parse for the application

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.parse.Parse;

/**
 * Created by Michael on 10/17/2015.
 */
public class ParseInitialization extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Iv1Ub7IhyBvWXo8jvrWPJ0E8YhRQdTgIrGJklzOx", "AKRV6m6gEY3A4yt65r7dCQHGKTMfmh9fqMgAyEAO");
    }

}
