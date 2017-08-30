package com.example.prateek.weatherapplication.utility;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by Prateek on 28/08/17.
 */

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());

    }
}
