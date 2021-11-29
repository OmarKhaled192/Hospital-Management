package com.yom.hospitalmanagementyom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.yom.hospitalmanagementyom.R;

public class VerificationActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_verification );

    }

    public void verify(View view) {
        startActivity(new Intent( VerificationActivity.this, RegistrationActivity2.class));

    }
}
