package com.yom.hospitalmanagementyom.activity.registration;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivityRegistrationBinding;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Patient;
import java.util.Objects;


public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private ActivityResultLauncher<String> activityResultLauncher, activityResultLauncher2;
    private boolean check=false;
    private String profile;
    private DatePickerDialog datePickerDialog;
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );

        initDatePicker();

        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    profile=result.getPath();
                    binding.pickOfProfile.setImageURI(result);
                    check=true;
                }
        );

        activityResultLauncher2=registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if(result)
                        activityResultLauncher.launch("image/*");
                    else
                        TastyToast.makeText(getApplicationContext(),"NO", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                }
        );

        binding.Date.setOnClickListener(view -> datePickerDialog.show());


        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Gender));
        binding.Gender.setAdapter(adapterItems);

    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            binding.Date.setText(date);
        };
        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, 2000, 0, 25);
    }

    private String makeDateString(int day, int month, int year)
    {
        return day + " / " + month + " / " + year;
    }


    public void next(View view) {
        String name = Objects.requireNonNull(binding.Name.getText()).toString();
        String data = Objects.requireNonNull(binding.Date.getText()).toString();
        String password = Objects.requireNonNull(binding.Password.getText()).toString();
        String confirmPassword = Objects.requireNonNull(binding.confirmPassword.getText()).toString();
        String gender = binding.Gender.getText().toString();

        if(!check)
            TastyToast.makeText(getBaseContext(), getString(R.string.profile), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        else if (name.length() == 0)
            binding.box1.setError(getString(R.string.nameError));
        else if (data.length() == 0)
            binding.box2.setError(getString(R.string.telephoneError));
        else if (password.length() == 0)
            binding.box3.setError(getString(R.string.passwordError));
        else if (confirmPassword.length() == 0)
            binding.box4.setError(getString(R.string.confirmPasswordError));
        else if (!password.equals(confirmPassword)){
            binding.box3.setError(getString(R.string.notEqualPassword));
            binding.box4.setError(getString(R.string.notEqualPassword));
        }
        else if(gender.length() == 0)
            binding.box4.setError(getString(R.string.genderError));
        else {
            Patient patient=new Patient();
            patient.setProfile(profile);
            patient.setName(name);
            patient.setDOB(data);
            patient.setPassword(password);
            patient.setType(Constants.PATIENT);
            patient.setStatus(Constants.ONLINE);
            Intent intent = new Intent(RegistrationActivity.this, CommonRegistrationActivity.class);
            intent.putExtra(Constants.PATIENT_KEY, patient);
            intent.putExtra(Constants.ACTIVITY_KEY,Constants.PATIENT);
            startActivity(intent);
            finish();
        }
    }

    public void pick(View view) {
        activityResultLauncher2.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}