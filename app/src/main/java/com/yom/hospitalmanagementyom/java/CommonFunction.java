package com.yom.hospitalmanagementyom.java;

import android.content.Context;

import java.text.DateFormat;
import java.util.Calendar;

public class CommonFunction {
    private Context context;
    private static CommonFunction commonFunction;

    private CommonFunction(Context context){
        this.context=context;
    }

    public static CommonFunction getInstance(Context context){
        if(commonFunction==null)
            commonFunction=new CommonFunction(context);
        return commonFunction;
    }

    public String getTimeNow() {
        String Date = DateFormat.getDateInstance(DateFormat.LONG).format(Calendar.getInstance().getTime());
        Calendar calendar = Calendar.getInstance();
        String currentTime = "";
        if (calendar.getTime().getHours() > 12 && calendar.getTime().getHours() < 24)
            currentTime = (calendar.getTime().getHours() - 12) + ":" + calendar.getTime().getMinutes() + ":" + calendar.getTime().getSeconds() + " PM";
        else if (calendar.getTime().getHours() == 12)
            currentTime = "12:" + calendar.getTime().getMinutes() + ":" + calendar.getTime().getSeconds() + " PM";
        else if (calendar.getTime().getHours() == 0)
            currentTime = "12:" + calendar.getTime().getMinutes() + ":" + calendar.getTime().getSeconds() + " AM";
        else
            currentTime = calendar.getTime().getHours() + ":" + calendar.getTime().getMinutes() + ":" + calendar.getTime().getSeconds() + " AM";
        return Date + " at " + currentTime;
    }
}
