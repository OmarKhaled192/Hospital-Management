package com.yom.hospitalmanagementyom.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.yom.hospitalmanagementyom.databinding.ActivityChooseBinding;


public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        ActivityChooseBinding binding= ActivityChooseBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );

        binding.registrationForPatient.setOnClickListener(view -> {
            Intent intent=new Intent(getBaseContext(), RegistrationActivity.class);
            startActivity( intent );
        });

        binding.registrationForHospital.setOnClickListener(view -> {
            Intent intent=new Intent(getBaseContext(), RegistrationActivityForHospital.class);
            startActivity( intent );
        });
    }
}