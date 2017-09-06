package com.example.prateek.weatherapplication.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prateek.weatherapplication.R;
import com.example.prateek.weatherapplication.apicontroller.RemoteFetch;

import com.example.prateek.weatherapplication.utility.ArcTranslateAnimation;
import com.example.prateek.weatherapplication.utility.CityPreference;
import com.example.prateek.weatherapplication.utility.Constant;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Prateek on 01/09/17.
 */

/**
 * This is a fragment class
 */
public class WeatherFragment extends Fragment {

    Typeface weatherFont;

    TextView mTextViewCityName;
    TextView mTextViewMist;
    TextView mTextViewHumidity;
    TextView mTextViewPressure;
    TextView mTextViewMinTempMain;
    TextView mTextViewSunrise;
    TextView mTextViewSunset;
    TextView mTextViewWindSpeed;
    TextView mTextViewLastUpdatedDate;
    TextView mTextViewWeatherIcon;
    TextView mTextViewMaxTemp;
    TextView mTextViewMinTemp;
    TextView mTextViewWeekName;
    ImageView mImageViewSun;

    Handler mHandler;

    //For date format create a instance of it
    DateFormat mDateFormat = DateFormat.getDateTimeInstance();

    public WeatherFragment() {
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        /**
         * This function is used to initialize all views
         */
        initializeViews(rootView);

        /**
         * Translation animation Function for sun from co ordinate x to y for sunrise and sunset
         */
        sunInitializeAnimation();


        return rootView;

    }


    private void initializeViews(View rootView) {

        mTextViewCityName = (TextView) rootView.findViewById(R.id.mTextViewCityName);
        mTextViewHumidity = (TextView) rootView.findViewById(R.id.mTextViewHumidity);
        mTextViewPressure = (TextView) rootView.findViewById(R.id.mTextViewPressure);
        mTextViewMinTempMain = (TextView) rootView.findViewById(R.id.mTextViewMinTempMain);
        mTextViewSunrise = (TextView) rootView.findViewById(R.id.mTextViewSunrise);
        mTextViewSunset = (TextView) rootView.findViewById(R.id.mTextViewSunset);
        mTextViewWindSpeed = (TextView) rootView.findViewById(R.id.mTextViewWindSpeed);
        mTextViewLastUpdatedDate = (TextView) rootView.findViewById(R.id.mTextViewLastUpdated);
        mTextViewMist = (TextView) rootView.findViewById(R.id.mTextViewMist);
        mTextViewWeatherIcon = (TextView) rootView.findViewById(R.id.mTextViewWeatherIcon);
        mTextViewMaxTemp = (TextView) rootView.findViewById(R.id.mTextViewMaxTemp);
        mTextViewMinTemp = (TextView) rootView.findViewById(R.id.mTextViewMinTemp);
        mTextViewWeekName = (TextView) rootView.findViewById(R.id.mTextViewWeekName);
        mImageViewSun = (ImageView) rootView.findViewById(R.id.mImageViewSun);

        mTextViewWeatherIcon.setTypeface(weatherFont);


    }

    private void sunInitializeAnimation() {

        ArcTranslateAnimation arcTranslateAnimation = new ArcTranslateAnimation(0, 800, 0, -150);
        arcTranslateAnimation.setDuration(2000);
        arcTranslateAnimation.setFillAfter(true);
        mImageViewSun.setAnimation(arcTranslateAnimation);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * setting Typeface
         */
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");

        /**
         * Getting city preferences and send to updateWeatherData function
         */
        updateWeatherData(new CityPreference(getActivity()).getCity());
    }

    private void updateWeatherData(final String city) {

        /**
         * This thread is used to call apis and get response in json format
         */
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city, Constant.BASE_URL + Constant.OPEN_WEATHER_API);
                if (json == null) {
                    mHandler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    mHandler.post(new Runnable() {
                        public void run() {
                            /**
                             * This function is used to pass json data in renderWeather function for parsing
                             */
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json) {
        try {
            mTextViewCityName.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            JSONObject wind = json.optJSONObject("wind");

            mTextViewMist.setText(details.getString("description").toUpperCase(Locale.US));
            mTextViewWindSpeed.setText("Speed :" + wind.getString("speed") + " km/h");
            mTextViewPressure.setText("Pressure :" + main.getString("pressure") + " hPa");
            mTextViewMinTempMain.setText(
                    String.format("%.0f", main.getDouble("temp")) + " ℃");

            String mSunriseDate = mDateFormat.format(new Date(json.getJSONObject("sys").optLong("sunrise") * 1000));
            String mSunsetDate = mDateFormat.format(new Date(json.getJSONObject("sys").optLong("sunset") * 1000));
            mTextViewSunrise.setText(mSunriseDate);
            mTextViewSunset.setText(mSunsetDate);
            mTextViewHumidity.setText(main.getString("humidity") + " %");
            mTextViewMaxTemp.setText(String.format("%.0f", main.getDouble("temp_max")) + " ℃");
            mTextViewMinTemp.setText(String.format("%.0f", main.getDouble("temp_min")) + " ℃");

            mTextViewWeekName.setText(new SimpleDateFormat("EEEE").format(new Date(json.getLong("dt") * 1000)));
            String updatedOn = mDateFormat.format(new Date(json.getLong("dt") * 1000));
            mTextViewLastUpdatedDate.setText(updatedOn);


            /**
             * This function is used to getting weather icon as per APIs
             */
            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        mTextViewWeatherIcon.setText(icon);
    }

    public void changeCity(String city) {
        updateWeatherData(city);
    }


}
