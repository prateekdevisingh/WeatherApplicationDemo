package com.example.prateek.weatherapplication.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.prateek.weatherapplication.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by Prateek on 28/08/17.
 */


/**
 * This is Utility class to handle connection
 */
public class Utility {

    /**
     * This method return internet connection status (connected or not connected)
     *
     * @param context is Application context
     * @return true if connected false otherwise
     */
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
