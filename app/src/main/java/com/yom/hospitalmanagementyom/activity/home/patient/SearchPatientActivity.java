package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.yom.hospitalmanagementyom.databinding.ActivitySearchPatientBinding;

public class SearchPatientActivity extends AppCompatActivity {
    private ActivitySearchPatientBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}