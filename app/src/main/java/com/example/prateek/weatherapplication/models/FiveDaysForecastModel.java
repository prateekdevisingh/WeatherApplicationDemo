package com.example.prateek.weatherapplication.models;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Prateek on 24/08/17.
 */

public class FiveDaysForecastModel implements Serializable {
    String dt;


    String day;
    String min;
    String max;
    String night;
    String eve;
    String morn;

    String pressure;
    String humidity;


    String id;
    String main;
    String description;
    String icon;

    String speed;
    String deg;
    String clouds;
    String snow;
    Context context;


    public FiveDaysForecastModel(String dt, String day, String min, String max, String night, String eve, String morn, String pressure, String humidity, String id, String main, String description, String icon, String speed, String deg, String clouds, String snow, Context context) {
        this.dt = dt;
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
        this.pressure = pressure;
        this.humidity = humidity;
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.speed = speed;
        this.deg = deg;
        this.clouds = clouds;
        this.snow = snow;
        this.context = context;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getEve() {
        return eve;
    }

    public void setEve(String eve) {
        this.eve = eve;
    }

    public String getMorn() {
        return morn;
    }

    public void setMorn(String morn) {
        this.morn = morn;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getSnow() {
        return snow;
    }

    public void setSnow(String snow) {
        this.snow = snow;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
