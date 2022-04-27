package com.yom.hospitalmanagementyom.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.doctor.HomeDoctorActivity;
import com.yom.hospitalmanagementyom.activity.home.patient.HomePatientActivity;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivityLoginBinding;
import com.yom.hospitalmanagementyom.listeners.LoginListener;
import com.yom.hospitalmanagementyom.model.Constants;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity implements LoginListener {

    private ActivityLoginBinding binding;
    private int position;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );

        position = -1;

        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Jobs));
        binding.job.setAdapter(adapterItems);

        binding.job.setOnItemClickListener((adapterView, view, i, l) -> position = i);
        repository= new Repository(this);

        binding.forgetPassword.setOnClickListener(view -> {
            if(Objects.requireNonNull(binding.Email.getText()).length()==0)
                repository.resetPassword(binding.Email.getText().toString());
            else
                TastyToast.makeText(getApplicationContext(),getString(R.string.emailError),TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        });

        binding.login.setOnClickListener(view -> login());

        binding.signUp.setOnClickListener(view -> {
            Intent intent=new Intent(getBaseContext(),ChooseActivity.class);
            startActivity( intent );
        });


    }

    void login() {
        String email= Objects.requireNonNull(binding.Email.getText()).toString();
        String password= Objects.requireNonNull(binding.Password.getText()).toString();
        if(position == -1){
            binding.job.setError(getString(R.string.jobError));
        }
        else if(email.length()==0) {
            binding.Email.setError(getString(R.string.emailError));
        }
        else if(password.length()==0) {
            binding.Password.setError(getString(R.string.passwordError));
        }
        else{
            repository.loginIn(email,password,this);
        }
    }


    @Override
    public void nextToHome() {
        if(position == 0) {
            repository.saveString(Constants.TYPE_USER, Constants.PATIENT);
            Intent intent=new Intent(getBaseContext(), HomePatientActivity.class);
            startActivity( intent );
            finishAffinity();
        }
        else if(position == 1) {
            repository.saveString(Constants.TYPE_USER, Constants.HOSPITAL);
            Intent intent=new Intent(getBaseContext(),HomePatientActivity.class);
            startActivity( intent );
            finishAffinity();
        }
        else if(position == 2) {
            repository.saveString(Constants.TYPE_USER, Constants.DOCTOR);
            Intent intent=new Intent(getBaseContext(), HomeDoctorActivity.class);
            startActivity( intent );
            finishAffinity();
        }
        else if(position == 3) {
            repository.saveString(Constants.TYPE_USER, Constants.ADMIN);
            Intent intent=new Intent(getBaseContext(),HomePatientActivity.class);
            startActivity( intent );
            finishAffinity();
        }
    }
}