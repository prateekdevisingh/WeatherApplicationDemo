package com.example.prateek.weatherapplication.utility;

import android.content.Context;
import android.content.SharedPreferences;



/**
 * Created by Prateek on 01/09/17.
 */


public class Prefs {

    /**
     * This method create {@link SharedPreferences.Editor}
     * @param context
     * @return {@link SharedPreferences.Editor}
     */
    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor;
    }

    /**
     * This method return {@link SharedPreferences}
     */
    private static SharedPreferences getSharePreferences(Context context) {
        return context.getSharedPreferences(Constant.APP_PREFERENCE, Context.MODE_PRIVATE);
    }

    /**
     * This method set string value to preference
     * @param context activity context
     * @param key key of value to store in {@link SharedPreferences}
     * @param value value to store with key
     */
    public static void setStringValueToPreferences(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    /**
     * This method get string value to preference
     * @param context activity context
     * @param key key of value
     * @return value that was stored with key
     */
    public static String getStringValueFromPreferences(Context context, String key) {
        return getSharePreferences(context).getString(key, "");
    }


    /**
     * This method check user is logon or not
     * @param context activity context
     * @return true if login false otherwise
     */
    public static boolean isLogin(Context context) {
        if (getStringValueFromPreferences(context, Constant.USER_LOGIN_TOKEN_PREF).length() > Constant.ZERO) {
            return true;
        } else {
            return false;
        }
    }



}
