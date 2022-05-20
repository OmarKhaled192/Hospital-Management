package com.yom.hospitalmanagementyom.functions;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {
    private final SharedPreferences sharedPreferences;

    public MySharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("Hospital Management", Activity.MODE_PRIVATE);
    }

    public void saveString(String key,String value) {
        SharedPreferences.Editor prefsEditor= sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.apply();
    }

    public String returnString(String key,String defValue) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, defValue);
        }
        return defValue;
    }

    public void saveInt(String key,int value) {
        SharedPreferences.Editor prefsEditor= sharedPreferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    public int returnInt(String key,int defValue) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getInt(key, defValue);
        }
        return defValue;
    }
}
