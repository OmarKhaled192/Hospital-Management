package com.yom.hospitalmanagementyom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yom.hospitalmanagementyom.R;


public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choose );
    }

    public void registrationForHospital(View view) {
        startActivity( new Intent(this,RegistrationActivityForHospital.class) );
    }

    public void registrationForPatient(View view) {
        startActivity( new Intent(this,RegistrationActivity.class)  );
    }
}