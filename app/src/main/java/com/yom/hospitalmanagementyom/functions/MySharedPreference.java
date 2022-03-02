package com.yom.hospitalmanagementyom.functions;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class MySharedPreference {
    private static MySharedPreference mySharedPreference;
    private final SharedPreferences sharedPreferences;

    private MySharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("Hospital Management", Activity.MODE_PRIVATE);
    }

    public static MySharedPreference newInstance(Context context){
        if(mySharedPreference==null) {
            mySharedPreference = new MySharedPreference(context);
        }
        return mySharedPreference;
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
}
