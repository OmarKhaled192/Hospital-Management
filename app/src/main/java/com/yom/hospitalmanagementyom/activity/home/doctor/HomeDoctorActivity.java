package com.yom.hospitalmanagementyom.activity.home.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.patient.favorite_tips.FavoriteTipsFragment;
import com.yom.hospitalmanagementyom.activity.home.patient.home.HomePatientFragment;
import com.yom.hospitalmanagementyom.databinding.ActivityHomeDoctorBinding;

public class HomeDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeDoctorBinding binding = ActivityHomeDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.profile));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.home));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.notification));


        binding.bottomNavigation.setOnShowListener(item -> {
            Fragment fragment=new HomePatientFragment();
            switch (item.getId()){
                case 1:
                    fragment=new ProfileDoctorFragment();
                    break;
                case 2:
                    fragment=new ProfileDoctorFragment();
                    break;
                case 3:
                    fragment=new ProfileDoctorFragment();
                    break;
                default:
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayoutDoctor,fragment)
                    .commit();
        });

        binding.bottomNavigation.show(2,true);
        binding.bottomNavigation.setCount(3,"100");

        binding.bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                binding.bottomNavigation.show(item.getId(),true);
            }
        });
    }
}