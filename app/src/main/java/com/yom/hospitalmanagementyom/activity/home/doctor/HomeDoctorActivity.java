package com.yom.hospitalmanagementyom.activity.home.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.hospital.Covid19Fragment;
import com.yom.hospitalmanagementyom.databinding.ActivityHomeDoctorBinding;
import com.yom.hospitalmanagementyom.fragments.doctor.CreatePostFragment;
import com.yom.hospitalmanagementyom.fragments.doctor.HomeDoctorFragment;
import com.yom.hospitalmanagementyom.fragments.doctor.ProfileDoctorFragment;
import com.yom.hospitalmanagementyom.fragments.doctor.TimesDoctorFragment;
import com.yom.hospitalmanagementyom.fragments.patient.ChatFragment;

public class HomeDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeDoctorBinding binding = ActivityHomeDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.profile));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.edit));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.home));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.time));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.chat));


        binding.bottomNavigation.setOnShowListener(item -> {
            Fragment fragment=new HomeDoctorFragment();
            switch (item.getId()){
                case 1:
                    fragment=new ProfileDoctorFragment();
                    break;
                case 2:
                    fragment=new CreatePostFragment();
                    break;
                case 3:
                    fragment=new Covid19Fragment();
                    break;
                case 4:
                    fragment=new TimesDoctorFragment();
                    break;
                case 5:
                    fragment=new ChatFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayoutDoctor,fragment)
                    .commit();
        });

        binding.bottomNavigation.show(3,false);
        //binding.bottomNavigation.setCount(3,"100");

        binding.bottomNavigation.setOnClickMenuListener(item ->
                binding.bottomNavigation.show(item.getId(),true));
    }
}