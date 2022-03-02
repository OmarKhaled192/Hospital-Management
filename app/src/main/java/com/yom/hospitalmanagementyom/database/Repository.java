package com.yom.hospitalmanagementyom.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.yom.hospitalmanagementyom.functions.CommonFunction;
import com.yom.hospitalmanagementyom.functions.MySharedPreference;

public class Repository {
    private Context context;
    private static Repository repository;
    private MySharedPreference mySharedPreference;

    private Repository(Context context){
        this.context=context;
        mySharedPreference=MySharedPreference.newInstance(context);
    }

    public static Repository newInstance(Context context){
        if(repository==null){
            repository=new Repository(context);
        }
        return repository;
    }

    public void saveStringSharedPreference(String key,String value) {
        mySharedPreference.saveString(key,value);
    }

    public String returnStringSharedPreference(String key,String defValue) {
        return mySharedPreference.returnString(key, defValue);
    }


}
