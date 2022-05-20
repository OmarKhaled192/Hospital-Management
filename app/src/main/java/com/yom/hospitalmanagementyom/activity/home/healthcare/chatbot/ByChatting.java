package com.yom.hospitalmanagementyom.activity.home.healthcare.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.patient.HomePatientActivity;
import com.yom.hospitalmanagementyom.database.Repository;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;

public class ByChatting extends AppCompatActivity {
    TextInputLayout notifyChatBox, notifyTimeBox;
    AppCompatAutoCompleteTextView notifyChat, notifyTime;
    private String String_No;
     Button TimeButton,nextBtn,create_OR_edit;

    String [] times;
    String [] temps;

    int[] totalHours, totalMinutes;
    int tag;
    String amPm;
    int time;
    private TimePickerDialog timePickerDialog;
    private int No ;
    int hour,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_chatting);
        notifyChat =findViewById(R.id.notify_chat_no);
        notifyTime =findViewById(R.id.Time);
        create_OR_edit = findViewById(R.id.create_OR_edit);
         initTimePicker();
        time = 0;
        tag =0;
        totalHours = new int[]{};
        totalMinutes= new int[]{};
        nextBtn = findViewById(R.id.nextBtn);
        notifyChatBox =findViewById(R.id.notify_chat_box);
        notifyTimeBox =findViewById(R.id.timeBox);
        TimeButton =findViewById(R.id.setTimeButton);
        amPm = new String();
        temps =  new String[] {"Set Time 2","Set Time 3","Set Time 4","Set Time 5",
                                "Set Time 6","Set Time 7","Set Time 8","Set Time 9","Set Time 10",};

        times = new String[]{"","","","","","","","","","","","","","","","","",""};
    }
    private void initTimePicker()
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                String timeFormat;

                timeFormat = makeTimeString(hour, minute,amPm);
                notifyTime.setText(timeFormat);
            }
        };


        timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,false);
        timePickerDialog.setTitle("Select Time");


    }
    public void createFn(View view) {
        TimeButton.setText("Set Time 1");
        if(tag ==0)
        {
            time = 0;

            String_No = notifyChat.getText().toString();
            No = Integer.parseInt(String_No);
            notifyChat.setEnabled(false);
            TimeButton.setEnabled(true);
            nextBtn.setEnabled(true);
            create_OR_edit.setText("Edit");
            tag =1;
        }
    else {
            time = 0;
            notifyChat.setEnabled(true);
            TimeButton.setEnabled(false);
            nextBtn.setEnabled(false);
            create_OR_edit.setText("Create");
            tag =0;

        }
    }
    private String makeTimeString(int hours, int minutes,String amPm)
    {

        String x,y;

        //saving original time
        totalHours[time] =  hours;
        totalMinutes[time] = minutes;


        //reformatting time
            //1.minutes:
        if(minutes < 10)
            y ="0" + minutes ;

        else
            y = minutes+"";

            //2.hours:
        if (hour >= 12) {
            amPm = " PM";
            hour -= 12;
        }

        else
            amPm =" AM";


        if(hours < 10)
            x ="0" + hours ;

        else
            x = hours+"";

        return x + " : " + y + amPm ;
    }
    public void TimeMake(View v)
    {
        timePickerDialog.show();
    }

    public void next(View v)
    {

        times[time] = notifyTime.getText().toString();
        TimeButton.setText(temps[time]);

        if (time < No-2)
            time = time+1;
        else
        {
            TastyToast.makeText(this,"submitted All times",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();


           Repository repository = new Repository(getApplicationContext());
           for(int i=0;i<No;i++)
           {
               repository.saveInt("Minute"+i,totalMinutes[i]);
               repository.saveInt("Hour"+i,totalHours[i]);
           }

           repository.setReceiver(getApplicationContext(),totalHours[0],totalMinutes[0]);
            repository.saveInt("TotalTime",No);
            repository.saveInt("TimeNow",0);
            Intent intent=new Intent(getBaseContext(), HomePatientActivity.class);
            startActivity( intent );
        }

        if(time == No - 3)
            nextBtn.setText(getString(R.string.finish));

    }
private void nextTime(int i){
    Repository repository = new Repository(getApplicationContext());
    repository.setReceiver(getApplicationContext(),totalHours[i+1],totalMinutes[i+1]);


}

}