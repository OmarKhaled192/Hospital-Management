package com.yom.hospitalmanagementyom.activity.registration;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.databinding.ActivityRegistrationBinding;
import com.yom.hospitalmanagementyom.model.Patient;

import java.io.Serializable;


public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private ActivityResultLauncher<String> activityResultLauncher;
    private boolean check=false;
    private String profile,name,telephone,password,confirmPassword;
    private final String PATIENT_KEY="Patient";
    private final String ACTIVITY_KEY="Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        profile=result.getPath();
                        binding.Profile.setImageURI(result);
                        check=true;
                    }
                }
        );
    }


    public void next(View view) {
        name=binding.Name.getText().toString();
        telephone=binding.Telephone.getText().toString();
        password=binding.Password.getText().toString();
        confirmPassword=binding.confirmPassword.getText().toString();
        if(!check)
            TastyToast.makeText(getBaseContext(), getString(R.string.profile), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        else if (name.length() == 0)
            binding.box1.setError(getString(R.string.nameError));
        else if (telephone.length() == 0)
            binding.box2.setError(getString(R.string.telephoneError));
        else if (password.length() == 0)
            binding.box3.setError(getString(R.string.passwordError));
        else if (confirmPassword.length() == 0)
            binding.box4.setError(getString(R.string.confirmPasswordError));
        else if (!password.equals(confirmPassword)){
            binding.box3.setError(getString(R.string.notEqualPassword));
            binding.box4.setError(getString(R.string.notEqualPassword));
        }
        else {
            Patient patient=new Patient();
            patient.setProfile(profile);
            patient.setName(name);
            //patient.setEarthPhone(telephone);
            patient.setPassword(password);
            patient.setType(PATIENT_KEY);
            Intent intent = new Intent(RegistrationActivity.this, CommonRegistrationActivity.class);
            intent.putExtra(PATIENT_KEY, (Serializable) patient);
            intent.putExtra(ACTIVITY_KEY,PATIENT_KEY);
            startActivity(intent);
            finish();
        }
    }

    public void pick(View view) {
        activityResultLauncher.launch("image/*");
    }
}