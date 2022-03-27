package com.yom.hospitalmanagementyom.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.yom.hospitalmanagementyom.activity.home.patient.HomePatientActivity;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );

        repository=Repository.newInstance(this);

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep( 1000 );
                    checkUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    void checkUser(){
        Intent intent=new Intent();
        String typeUser=repository.returnStringSharedPreference("TypeUser","");
        if(repository.getUser()!=null){
            switch (typeUser) {
                case "Patient":
                    intent.setClass(this, HomePatientActivity.class);
                    break;
                case "Hospital":
                    intent.setClass(this, VerificationActivity.class);
                    break;
                case "Doctor":
                    intent.setClass(this, RegistrationActivity.class);
                    break;
                case "Admin":
                    intent.setClass(this, RegistrationActivityForHospital.class);
                    break;
                default:
                    intent.setClass(this, SlideActivity.class);
                    break;
            }
        }
        else
            intent.setClass(this, HomePatientActivity.class);

        startActivity(intent);
        finish();
    }


}