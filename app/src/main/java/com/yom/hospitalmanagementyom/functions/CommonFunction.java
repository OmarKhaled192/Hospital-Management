package com.yom.hospitalmanagementyom.functions;

import android.app.Dialog;
import android.content.Context;
import com.yom.hospitalmanagementyom.R;
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

    public Dialog getDialog(Context context){
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.loading);
        return dialog;
    }
}
