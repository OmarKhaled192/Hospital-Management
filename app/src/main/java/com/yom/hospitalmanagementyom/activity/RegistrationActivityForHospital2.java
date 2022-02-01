package com.yom.hospitalmanagementyom.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.yom.hospitalmanagementyom.R;

import java.util.concurrent.TimeUnit;

public class RegistrationActivityForHospital2 extends AppCompatActivity {
    ImageView signPhone,signEmail;
    EditText phone ;
    Button BtnVerifyPhone,BtnVerifyEmail;
    ConstraintLayout emailBox;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_for_hosbital2);
        phone = findViewById(R.id.phoneMR2);
        signPhone = findViewById(R.id.signPhone);
        signEmail = findViewById(R.id.signEmail);

         BtnVerifyPhone = findViewById(R.id.BtnVerifyPhone);
         BtnVerifyEmail = findViewById(R.id.BtnVerifyEmail);

         emailBox = findViewById(R.id.emailBox);

     }

    public void gotoSignIn(View v)
    {
        startActivity( new Intent(this,LoginActivity.class)  );
    }

    public void returnToRegHos1(View view) {
        //startActivity( new Intent(this,RegistrationActivityForHospital.class)  );
        super.onBackPressed();
     }


    public void verifyPhone(View view) {
        Intent intent = new Intent(this,VerificationActivity.class);
       intent.putExtra("phone",phone.getText().toString());
        intent.putExtra("Activity","RegisterForHos");
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 )
        {
            signPhone.setVisibility(View.VISIBLE);

            if(resultCode == RESULT_OK)
            {
                signPhone.setImageResource(R.drawable.ic_baseline_done_24);
                BtnVerifyPhone.setEnabled(false);
                phone.setEnabled(false);
                emailBox.setVisibility(View.VISIBLE);
            }
             else if(resultCode == RESULT_CANCELED)
                signPhone.setImageResource(R.drawable.ic_baseline_close_24);


        }

    }
}