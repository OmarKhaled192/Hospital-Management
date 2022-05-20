package com.yom.hospitalmanagementyom.model;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.healthcare.Questions;
import com.yom.hospitalmanagementyom.database.Repository;

public class MyReceiver extends BroadcastReceiver {

    Repository repository;
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        repository = new Repository(context);
        showNotification();
        getTime();
    }
    private void showNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("healthcareChannel id", "health care CHANNEL", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent intent = new Intent(context, Questions.class);

        PendingIntent  pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "healthcareChannel id");
        mBuilder.setSmallIcon(R.mipmap.logo_round);
        mBuilder.setContentTitle(context.getString(R.string.appName) );

        mBuilder.setContentText(context.getString(R.string.Healthnotify));
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel( true );
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, mBuilder.build());
    }
    private void getTime(){
        int number = repository.returnInt("TotalTime",0);
        int now = repository.returnInt("TimeNow",0);
        int[] hours=new int[]{};
        int[] minutes=new int[]{};
        for(int i=0; i<number; i++){
            hours[i]= repository.returnInt("Hour"+i,0);
            minutes[i]=repository.returnInt("Minute"+i,0);
        }
        if(now==number){
            repository.saveInt("TimeNow",0);
            now = 0;
        }
        if(now<number){
            repository.setReceiver(context, hours[now+1],minutes[now+1]);
            repository.saveInt("TimeNow",now+1);
        }

    }
}
