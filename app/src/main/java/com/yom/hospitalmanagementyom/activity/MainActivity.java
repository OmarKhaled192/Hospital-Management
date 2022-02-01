package com.yom.hospitalmanagementyom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.java.MyDatabase;
import com.yom.hospitalmanagementyom.java.MySharedPreference;


public class MainActivity extends AppCompatActivity {

    MyDatabase myDatabase;
    MySharedPreference mySharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        myDatabase=new MyDatabase(this);
        mySharedPreference=new MySharedPreference(this);


    //    myDatabase.savePatient();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep( 1000 );
                    if(mySharedPreference.returnString("Login","No").equals("No")) {
                        Intent intent = new Intent(MainActivity.this, SlideActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(mySharedPreference.returnString("Login","No").equals("Yes")){
                        Intent intent = new Intent(MainActivity.this, HomeActivityForPatient.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}