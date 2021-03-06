package com.yom.hospitalmanagementyom.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.yom.hospitalmanagementyom.activity.home.doctor.HomeDoctorActivity;
import com.yom.hospitalmanagementyom.activity.home.hospital.AdminBoardActivity;
import com.yom.hospitalmanagementyom.activity.home.hospital.ViewActivity;
import com.yom.hospitalmanagementyom.activity.home.patient.HomePatientActivity;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivityMainBinding;
import com.yom.hospitalmanagementyom.model.Constants;

public class MainActivity extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );

        repository=new Repository(getApplicationContext());
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                    checkUser();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    void checkUser(){
        Intent intent=new Intent();
        String typeUser=repository.returnStringSharedPreference(Constants.TYPE_USER,"");
        if(repository.getUser()!=null){
            switch (typeUser) {
                case Constants.PATIENT:
                    intent.setClass(this, AdminBoardActivity.class);
                    break;
                case Constants.HOSPITAL:
                    intent.setClass(this, VerificationActivity.class);
                    break;
                case Constants.DOCTOR:
                    intent.setClass(this, HomeDoctorActivity.class);
                    break;
                case Constants.ADMIN:
                    intent.setClass(this, RegistrationActivityForHospital.class);
                    break;
                default:
                    intent.setClass(this, HomePatientActivity.class);
                    break;
            }
        }
        else
            intent.setClass(this, SlideActivity.class);

        startActivity(intent);

        finish();
    }


}