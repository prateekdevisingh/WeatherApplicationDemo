package com.example.prateek.weatherapplication.utility;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Prateek on 25/08/17.
 */


/**
 * This is a City preferences class
 */
public class CityPreference {
	
	SharedPreferences prefs;

	/**
	 * City preferences constructor
	 * @param activity
	 */
	public CityPreference(Activity activity){
		prefs = activity.getPreferences(Activity.MODE_PRIVATE);
	}


	/**
	 * This is getter and setter for city preference
	 * @return
	 */
	public String getCity(){
		return prefs.getString("city", "Delhi, IN");
	}
	
	public void setCity(String city){
		prefs.edit().putString("city", city).commit();
	}
	
}
