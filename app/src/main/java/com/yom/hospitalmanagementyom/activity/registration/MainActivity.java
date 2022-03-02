package com.yom.hospitalmanagementyom.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.patient.HomePatientActivity;
import com.yom.hospitalmanagementyom.database.MyRegistrationFirebase;
import com.yom.hospitalmanagementyom.functions.MySharedPreference;

public class MainActivity extends AppCompatActivity {

    MyRegistrationFirebase myRegistrationFirebase;
    MySharedPreference mySharedPreference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Intent intent;
    String TypeUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        myRegistrationFirebase= MyRegistrationFirebase.getInstance(this);
        mySharedPreference=MySharedPreference.newInstance(this);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseAuth.signOut();
        TypeUser=mySharedPreference.returnString("TypeUser","Patient");

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep( 1000 );
                    if(firebaseUser!=null){
                        if(TypeUser.equals("Patient"))
                            intent = new Intent(MainActivity.this, HomePatientActivity.class);
                        else if(TypeUser.equals("Hospital"))
                            intent = new Intent(MainActivity.this, SlideActivity.class);
                        else if(TypeUser.equals("Doctor"))
                            intent = new Intent(MainActivity.this, SlideActivity.class);
                        else if(TypeUser.equals("Admin"))
                            intent = new Intent(MainActivity.this, SlideActivity.class);
                    }
                    else
                        intent = new Intent(MainActivity.this, SlideActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}