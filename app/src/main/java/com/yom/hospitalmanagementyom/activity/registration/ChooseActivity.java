package com.yom.hospitalmanagementyom.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.yom.hospitalmanagementyom.databinding.ActivityChooseBinding;
import com.yom.hospitalmanagementyom.model.Constants;


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

        binding.registrationForDoctor.setOnClickListener(view -> {
            Intent intent=new Intent(getBaseContext(), RegistrationActivity.class);
            intent.putExtra(Constants.ACTIVITY,Constants.DOCTOR);
            startActivity( intent );
        });

        binding.registrationForAdmin.setOnClickListener(view -> {
            Intent intent=new Intent(getBaseContext(), RegistrationActivityForHospital.class);
            intent.putExtra(Constants.ACTIVITY,Constants.ADMIN);
            startActivity( intent );
        });
    }
}