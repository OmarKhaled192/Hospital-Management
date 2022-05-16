package com.yom.hospitalmanagementyom.functions;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.model.MyReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
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

    public void setReceiver(Context context,int hour,int minute){

         Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);


      //  calendar.add(Calendar.AM_PM,Calendar.PM);
        //------------------------------------------------------
        Intent alertIntent = new Intent(context, MyReceiver.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                PendingIntent.getBroadcast(context, 0, alertIntent,PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(context,"1",Toast.LENGTH_LONG).show();
    }
    public boolean checkExistId(List<String> list, String id){
        for (int i=0; i<list.size(); i++){
            if(list.get(i).equals(id))
                return true;
        }
        return false;
    }
}
