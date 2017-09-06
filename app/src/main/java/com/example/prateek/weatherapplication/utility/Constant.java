package com.example.prateek.weatherapplication.utility;

/**
 * Created by Prateek on 25/08/17.
 */


/**
 * This is Constant class where all the static variables with their datatype are stored
 */
public class Constant {

    /**
     * API request Urls
     */
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String OPEN_WEATHER_API =
            "weather?q=%s&units=metric";
    public static final String OPEN_FORECAST_API =
            "forecast/daily?q=%s&units=metric";
    public static final String OPEN_WEATHER_API_ID = "cc5b1d8567d6d82c0d7f697a53628e62";

    /**
     * Shared Preferences
     */
    public static final String APP_PREFERENCE = "wapp_preference";
    public static final String USER_LOGIN_TOKEN_PREF = "login_pref";
    public static final String CITY_PREF = "city";

    /**
     * Key for city
     */
    public static final String CITY_KEY = "CITY";

    /**
     * Constant values for request
     */
    public static final int ZERO = 0;
    public final static int PLAY_SERVICES_REQUEST = 1000;
    public final static int REQUEST_CHECK_SETTINGS = 2000;


}
