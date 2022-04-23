package com.yom.hospitalmanagementyom.functions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CommonFunction {
    private final Calendar calendar;
    private final SimpleDateFormat simpleDateFormat;

    public CommonFunction(){
        calendar=Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy & KK:mm:ss aaa", Locale.ENGLISH);
    }

    public String getTimeNow() {
        return simpleDateFormat.format(calendar.getTime());
    }
}
