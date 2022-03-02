package com.yom.hospitalmanagementyom.functions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CommonFunction {
    private static CommonFunction commonFunction;
    private final Calendar calendar;
    private final SimpleDateFormat simpleDateFormat;

    private CommonFunction(){
        calendar=Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy & KK:mm:ss aaa", Locale.ENGLISH);
    }

    public static CommonFunction newInstance(){
        if(commonFunction==null)
            commonFunction=new CommonFunction();
        return commonFunction;
    }

    public String getTimeNow() {
        return simpleDateFormat.format(calendar.getTime());
    }
}
