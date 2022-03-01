package com.yom.hospitalmanagementyom.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.yom.hospitalmanagementyom.databinding.ActivityHospitalViewingForPatientBinding;

public class HospitalViewingActivityForPatient extends AppCompatActivity {

    private ActivityHospitalViewingForPatientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHospitalViewingForPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*setSupportActionBar(binding.toolbarChoose);
        binding.toolbarChoose.setNavigationIcon(R.drawable.back);
        binding.toolbarChoose.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

         */
    }
}
