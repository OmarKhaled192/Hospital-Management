package com.yom.hospitalmanagementyom.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.patient.HomePatientActivity;
import com.yom.hospitalmanagementyom.databinding.ActivityLoginBinding;
import com.yom.hospitalmanagementyom.database.MyRegistrationFirebase;
import com.yom.hospitalmanagementyom.functions.MySharedPreference;
import com.yom.hospitalmanagementyom.listeners.LoginListener;


public class LoginActivity extends AppCompatActivity implements LoginListener {

    private ActivityLoginBinding binding;
    private String choose;
    private MyRegistrationFirebase myRegistrationFirebase;
    private MySharedPreference mySharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mySharedPreference=new MySharedPreference(this);
        binding.job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choose= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                choose= (String) parent.getItemAtPosition(0);
            }
        });
        myRegistrationFirebase=MyRegistrationFirebase.getInstance(this);
    }

    public void login(View view) {
        String email=binding.Email.getText().toString();
        String password=binding.Password.getText().toString();
        if(email.length()==0) {
            binding.Email.setError(getString(R.string.emailError));
        }
        else if(password.length()==0) {
            binding.Password.setError(getString(R.string.passwordError));
        }
        else{
            myRegistrationFirebase.SignInUser(email,password,this);
        }
    }

    public void forgetPassword(View view) {
        myRegistrationFirebase.resetPassword(binding.Email.getText().toString());
    }

    public void signUp(View view) {
        Intent intent=new Intent(getBaseContext(),ChooseActivity.class);
        startActivity( intent );
    }

    @Override
    public void nextToHome() {
        if(choose.equals( getResources().getStringArray(R.array.Jobs)[0] ) ) {
            mySharedPreference.saveString("TypeUser", "Patient");
            Intent intent=new Intent(getBaseContext(), HomePatientActivity.class);
            startActivity( intent );
            finishAffinity();
        }
        else if(choose.equals( getResources().getStringArray(R.array.Jobs)[1] )) {
            mySharedPreference.saveString("TypeUser", "Hospital");
            Intent intent=new Intent(getBaseContext(),HomePatientActivity.class);
            startActivity( intent );
            finishAffinity();
        }
        else if(choose.equals( getResources().getStringArray(R.array.Jobs)[2] )) {
            mySharedPreference.saveString("TypeUser", "Doctor");
            Intent intent=new Intent(getBaseContext(),HomePatientActivity.class);
            startActivity( intent );
            finishAffinity();
        }
        else if(choose.equals( getResources().getStringArray(R.array.Jobs)[3] )) {
            mySharedPreference.saveString("TypeUser", "Admin");
            Intent intent=new Intent(getBaseContext(),HomePatientActivity.class);
            startActivity( intent );
            finishAffinity();
        }
    }
}