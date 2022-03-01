package com.yom.hospitalmanagementyom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.databinding.ActivityVerificationBinding;
import com.yom.hospitalmanagementyom.java.MyRegistrationFirebase;
import com.yom.hospitalmanagementyom.listeners.PhoneVerificationListener;


public class VerificationActivity extends AppCompatActivity implements PhoneVerificationListener {

    private ActivityVerificationBinding binding;
    private String phoneNumber, email, password;
    private String total;
    private Intent intent;
    private final String PHONE_KEY="Phone";
    private final String EMAIL_KEY="Email";
    private final String VERIFY_KEY="Verify";
    private final String PASSWORD_KEY="Password";
    private MyRegistrationFirebase myRegistrationFirebase;
    private String getVERIFY_KEY;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityVerificationBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getVERIFY_KEY=getIntent().getExtras().getString(VERIFY_KEY);
        myRegistrationFirebase= MyRegistrationFirebase.getInstance(this);
        intent = new Intent();
        checkPhoneOrEmail();
        handlingEditText();
    }
    private void checkPhoneOrEmail(){
        if(getVERIFY_KEY.equals(PHONE_KEY)) {
            binding.verifyCodeText.setText(getString(R.string.enterCode));
            binding.Num1.setVisibility(View.VISIBLE);
            binding.Num2.setVisibility(View.VISIBLE);
            binding.Num3.setVisibility(View.VISIBLE);
            binding.Num4.setVisibility(View.VISIBLE);
            binding.Num5.setVisibility(View.VISIBLE);
            binding.Num6.setVisibility(View.VISIBLE);
            phoneNumber = getIntent().getExtras().getString(PHONE_KEY);
            myRegistrationFirebase.startPhoneNumberVerification(phoneNumber,this);
            TastyToast.makeText(getBaseContext(), "Send Message", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }
        else if(getVERIFY_KEY.equals(EMAIL_KEY)) {
            binding.verifyCodeText.setText(getString(R.string.enterCode1));
            binding.Num1.setVisibility(View.GONE);
            binding.Num2.setVisibility(View.GONE);
            binding.Num3.setVisibility(View.GONE);
            binding.Num4.setVisibility(View.GONE);
            binding.Num5.setVisibility(View.GONE);
            binding.Num6.setVisibility(View.GONE);
            email = getIntent().getExtras().getString(EMAIL_KEY);
            password = getIntent().getExtras().getString(PASSWORD_KEY);
            myRegistrationFirebase.createEmail(email,password);
        }
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

    public void verify(View view) {
        if(getVERIFY_KEY.equals(PHONE_KEY)) {
            verifyPhone();
        }
        else if(getVERIFY_KEY.equals(EMAIL_KEY)) {
            verifyEmail();
        }
    }

    private void verifyPhone(){
        total = binding.Num1.getText().toString() + binding.Num2.getText().toString() +
                binding.Num3.getText().toString() + binding.Num4.getText().toString() +
                binding.Num5.getText().toString() + binding.Num6.getText().toString();
        if (total.length() != 6)
            TastyToast.makeText(this, getString(R.string.enterCode), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        else {
            myRegistrationFirebase.signInUser(total,this);
        }
    }

    private void verifyEmail(){
        if(myRegistrationFirebase.isVerify()){
            setResult(RESULT_OK,intent);
        } else {
            setResult(RESULT_CANCELED,intent);
        }
        VerificationActivity.super.onBackPressed();
    }


    public void resend(View view) {
        myRegistrationFirebase.startPhoneNumberVerification(phoneNumber,this);
        Toast.makeText(this,getString(R.string.resend),Toast.LENGTH_LONG).show();
    }

    @Override
    public void successVerify() {
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }

    @Override
    public void failVerify() {
        setResult(RESULT_CANCELED,intent);
        super.onBackPressed();
    }
}
