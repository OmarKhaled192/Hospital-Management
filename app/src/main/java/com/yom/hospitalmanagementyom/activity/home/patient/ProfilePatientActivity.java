package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.yom.hospitalmanagementyom.databinding.ActivityProfilePatientBinding;

public class ProfilePatientActivity extends AppCompatActivity {

    private ActivityProfilePatientBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilePatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}