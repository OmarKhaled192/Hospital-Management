package com.yom.hospitalmanagementyom.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.databinding.ActivityChooseBinding;


public class ChooseActivity extends AppCompatActivity {

    private ActivityChooseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding= ActivityChooseBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );
        setSupportActionBar(binding.toolbarChoose);
        binding.toolbarChoose.setNavigationIcon(R.drawable.back);
        binding.toolbarChoose.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void registrationForHospital(View view) {
        Intent intent=new Intent(getBaseContext(),RegistrationActivityForHospital.class);
        startActivity( intent );
    }

    public void registrationForPatient(View view) {
        Intent intent=new Intent(getBaseContext(),RegistrationActivity.class);
        startActivity( intent );
    }
}