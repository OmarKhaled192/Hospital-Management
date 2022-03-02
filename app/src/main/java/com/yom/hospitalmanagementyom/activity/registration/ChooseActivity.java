package com.yom.hospitalmanagementyom.activity.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.RegistrationActivity;
import com.yom.hospitalmanagementyom.activity.RegistrationActivityForHospital;
import com.yom.hospitalmanagementyom.databinding.ActivityChooseBinding;


public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        ActivityChooseBinding binding= ActivityChooseBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    public void registrationForHospital(View view) {
        Intent intent=new Intent(getBaseContext(), RegistrationActivityForHospital.class);
        startActivity( intent );
    }

    public void registrationForPatient(View view) {
        Intent intent=new Intent(getBaseContext(), RegistrationActivity.class);
        startActivity( intent );
    }
}