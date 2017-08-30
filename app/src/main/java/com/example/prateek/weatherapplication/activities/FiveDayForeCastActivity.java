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
 * Created by Prateek on 24/08/17.
 */

public class FiveDayForeCastActivity extends AppCompatActivity {

    Typeface weatherFont;
    Handler handler;

    private RecyclerView mRecyclerViewFiveDaysForecast;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.Adapter mAdapter;

    ArrayList<FiveDaysForecastModel> fiveDaysForecastArrayList;
    String mCountDays;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fivedaysforecast);
        initializeViews();

    }

    private void initializeViews() {

        mRecyclerViewFiveDaysForecast = (RecyclerView) findViewById(R.id.mRecyclerViewFiveDaysForecast);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewFiveDaysForecast.setLayoutManager(mLinearLayoutManager);
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weather.ttf");

        readAssestJson();
        updateWeatherData(new CityPreference(this).getCity());
    }

    private void readAssestJson() {
        try {
            InputStream inputStream = getAssets().open("city.list.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonData = new String(buffer, "UTF-8");

            JSONObject jsonobject = new JSONObject(jsonData);
            Log.e("","");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateWeatherData(final String city) {
        handler = new Handler();
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(getApplicationContext(), city, Constant.OPEN_FORECAST_MAP_API);
                if (json == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
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

    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
        String hint;
    }


}
