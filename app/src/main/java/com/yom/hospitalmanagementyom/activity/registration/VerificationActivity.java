package com.yom.hospitalmanagementyom.activity.registration;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.doctor.HomeDoctorActivity;
import com.yom.hospitalmanagementyom.activity.home.patient.HomePatientActivity;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivityVerificationBinding;
import com.yom.hospitalmanagementyom.listeners.PhoneVerificationListener;
import com.yom.hospitalmanagementyom.listeners.ReadMessage;
import com.yom.hospitalmanagementyom.model.Constants;


public class VerificationActivity extends AppCompatActivity implements PhoneVerificationListener, ReadMessage {

    private ActivityVerificationBinding binding;
    private Intent intent;
    private Repository repository;
    private ActivityResultLauncher<String> launcher;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityVerificationBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );

        repository = new Repository(this);
        launcher=registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                o -> {
                    if(o) {
                        Cursor cursor = getContentResolver().query(Telephony.Sms.CONTENT_URI,
                                null, null, null, null);
                        cursor.moveToFirst();
                        String Numbers = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
                        Log.d("Joo",Numbers);
                        cursor.close();
                        String num1=Numbers.charAt(0)+"";
                        String num2=Numbers.charAt(1)+"";
                        String num3=Numbers.charAt(2)+"";
                        String num4=Numbers.charAt(3)+"";
                        String num5=Numbers.charAt(4)+"";
                        String num6=Numbers.charAt(5)+"";
//                        binding.Num1.setText(num1);
//                        binding.Num2.setText(num2);
//                        binding.Num3.setText(num3);
//                        binding.Num4.setText(num4);
//                        binding.Num5.setText(num5);
//                        binding.Num6.setText(num6);
                    }
                }
        );
        checkPhoneOrEmail();
        handlingEditText();

        intent = new Intent();
        binding.verify.setOnClickListener(view -> {
            if(getIntent().getExtras().getString(Constants.VERIFY).equals(Constants.PHONE))
                verifyPhone();
            else if(getIntent().getExtras().getString(Constants.VERIFY).equals(Constants.EMAIL))
                verifyEmail();
        });

        binding.resend.setOnClickListener(view -> {
            checkPhoneOrEmail();
            Toast.makeText(getApplicationContext(),getString(R.string.resend),Toast.LENGTH_LONG).show();
        });
    }


    private void checkPhoneOrEmail(){
        if(getIntent().getExtras().getString(Constants.VERIFY).equals(Constants.PHONE)) {
            editTextVisible(View.VISIBLE);
            String phoneNumber = getIntent().getExtras().getString(Constants.PHONE);
            repository.startPhoneNumberVerification(phoneNumber,this, this);
        }
        else if(getIntent().getExtras().getString(Constants.VERIFY).equals(Constants.EMAIL)) {
            editTextVisible(View.GONE);
            String email = getIntent().getExtras().getString(Constants.EMAIL);
            String password = getIntent().getExtras().getString(Constants.PASSWORD);
            repository.createEmail(email, password);
        }
    }

    private void editTextVisible(int visible){
        binding.Num1.setVisibility(visible);
        binding.Num2.setVisibility(visible);
        binding.Num3.setVisibility(visible);
        binding.Num4.setVisibility(visible);
        binding.Num5.setVisibility(visible);
        binding.Num6.setVisibility(visible);
        if(visible == View.VISIBLE)
            binding.verifyCodeText.setText(getString(R.string.enterCode));
        else if(visible == View.GONE)
            binding.verifyCodeText.setText(getString(R.string.enterCode1));

    }

    private void handlingEditText(){
        binding.Num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.Num2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.Num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.Num3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.Num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.Num4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.Num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.Num5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.Num5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.Num6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void verifyPhone(){
        String total = binding.Num1.getText().toString() + binding.Num2.getText().toString() +
                binding.Num3.getText().toString() + binding.Num4.getText().toString() +
                binding.Num5.getText().toString() + binding.Num6.getText().toString();
        if (total.length() != 6)
            TastyToast.makeText(this, getString(R.string.enterCode), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        else {
            repository.signInUserByPhone(total,this);
        }
    }

    private void verifyEmail(){
        if(repository.isVerify()){
            setResult(RESULT_OK,intent);
        } else {
            setResult(RESULT_CANCELED,intent);
        }
        VerificationActivity.super.onBackPressed();
    }


    @Override
    public void successVerify() {
//        repository.deleteUserPhone();
//        setResult(RESULT_OK,intent);
        Intent i= new Intent(getApplicationContext(), HomePatientActivity.class);
        startActivity(i);
        fileList();
//        super.onBackPressed();
    }

    @Override
    public void failVerify() {
//        setResult(RESULT_CANCELED,intent);
//        super.onBackPressed();
        Intent i= new Intent(getApplicationContext(), HomePatientActivity.class);
        startActivity(i);
        fileList();
    }

    @Override
    public void setNumbers() {
        launcher.launch(Manifest.permission.READ_SMS);
    }
}
