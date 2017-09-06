package com.example.prateek.weatherapplication.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.prateek.weatherapplication.R;
import com.example.prateek.weatherapplication.adapters.FiveDaysForecastAdapter;
import com.example.prateek.weatherapplication.apicontroller.RemoteFetch;
import com.example.prateek.weatherapplication.models.FiveDaysForecastModel;
import com.example.prateek.weatherapplication.utility.CityPreference;
import com.example.prateek.weatherapplication.utility.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prateek on 25/08/17.
 */


/**
 * This class is used to display forecast data
 */
public class FiveDayForeCastActivity extends AppCompatActivity {

    Typeface mWeatherFont;
    Handler mHandler;

    private RecyclerView mRecyclerViewFiveDaysForecast;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.Adapter mAdapter;

    ArrayList<FiveDaysForecastModel> fiveDaysForecastArrayList;
    String mCountDays;
    String mCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fivedaysforecast);

        try {
            if(getIntent().getStringExtra(Constant.CITY_KEY)!=null){
                mCity = getIntent().getStringExtra(Constant.CITY_KEY);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        /**
         * This function is used to initialize all the views
         */
        initializeViews();

    }

    private void initializeViews() {

        mRecyclerViewFiveDaysForecast = (RecyclerView) findViewById(R.id.mRecyclerViewFiveDaysForecast);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewFiveDaysForecast.setLayoutManager(mLinearLayoutManager);
        mWeatherFont = Typeface.createFromAsset(getAssets(), "fonts/weather.ttf");


        /**
         * This function is used to update Weather Data by passing city
         */
        updateWeatherData(mCity);
    }


    private void updateWeatherData(final String city) {
        mHandler = new Handler();
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(getApplicationContext(), city, Constant.BASE_URL + Constant.OPEN_FORECAST_API + "&appid=" + Constant.OPEN_WEATHER_API_ID);
                if (json == null) {
                    mHandler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    mHandler.post(new Runnable() {
                        public void run() {

                            /**
                             * This function is used to parse the weather data by passing json object
                             */
                            parseWeatherData(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void parseWeatherData(JSONObject json) {

        try {
            int cod = json.optInt("cod");
            if (cod == 200) {


                fiveDaysForecastArrayList = new ArrayList<>();


                mCountDays = json.optString("cnt");
                JSONArray jsonArray = json.optJSONArray("list");
                for (int index = 0; index < jsonArray.length(); index++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);

                    fiveDaysForecastArrayList.add(new FiveDaysForecastModel(
                            jsonObject.optString("dt"),
                            jsonObject.getJSONObject("temp").optString("day"),
                            jsonObject.getJSONObject("temp").optString("min"),
                            jsonObject.getJSONObject("temp").optString("max"),
                            jsonObject.getJSONObject("temp").optString("night"),
                            jsonObject.getJSONObject("temp").optString("eve"),
                            jsonObject.getJSONObject("temp").optString("morn"),
                            jsonObject.optString("pressure"),
                            jsonObject.optString("humidity"),
                            jsonObject.getJSONArray("weather").getJSONObject(0).optString("id"),
                            jsonObject.getJSONArray("weather").getJSONObject(0).optString("main"),
                            jsonObject.getJSONArray("weather").getJSONObject(0).optString("description"),
                            jsonObject.getJSONArray("weather").getJSONObject(0).optString("icon"),
                            jsonObject.optString("speed"),
                            jsonObject.optString("deg"),
                            jsonObject.optString("clouds"),
                            jsonObject.optString("snow"),
                            getApplicationContext()));


                }

                mAdapter = new FiveDaysForecastAdapter(this, fiveDaysForecastArrayList, mCountDays);
                mRecyclerViewFiveDaysForecast.setAdapter(mAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
