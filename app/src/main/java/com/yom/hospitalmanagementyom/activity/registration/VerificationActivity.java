package com.yom.hospitalmanagementyom.activity.registration;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.databinding.ActivityVerificationBinding;
import com.yom.hospitalmanagementyom.database.MyRegistrationFirebase;
import com.yom.hospitalmanagementyom.listeners.PhoneVerificationListener;
import com.yom.hospitalmanagementyom.listeners.ReadMessage;


public class VerificationActivity extends AppCompatActivity implements PhoneVerificationListener, ReadMessage {

    private ActivityVerificationBinding binding;
    private String phoneNumber;
    private Intent intent;
    private final String PHONE_KEY="Phone";
    private final String EMAIL_KEY="Email";
    private final String VERIFY_KEY="Verify";
    private final String PASSWORD_KEY="Password";
    private MyRegistrationFirebase myRegistrationFirebase;
    private String getVERIFY_KEY;
    private ActivityResultLauncher<String> launcher;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityVerificationBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.back);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());
        getVERIFY_KEY=getIntent().getExtras().getString(VERIFY_KEY);
        myRegistrationFirebase= new MyRegistrationFirebase(this);
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
            myRegistrationFirebase.startPhoneNumberVerification(phoneNumber,this, this);
            TastyToast.makeText(getBaseContext(), "Send Message", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            launcher=registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    o -> {
                        if(o) {
                            Cursor cursor = getContentResolver().query(Telephony.Sms.CONTENT_URI, null, null, null, null);
                            cursor.moveToFirst();
                            String Numbers = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
                            cursor.close();
                            String num1=Numbers.charAt(0)+"";
                            String num2=Numbers.charAt(1)+"";
                            String num3=Numbers.charAt(2)+"";
                            String num4=Numbers.charAt(3)+"";
                            String num5=Numbers.charAt(4)+"";
                            String num6=Numbers.charAt(5)+"";
                            binding.Num1.setText(num1);
                            binding.Num2.setText(num2);
                            binding.Num3.setText(num3);
                            binding.Num4.setText(num4);
                            binding.Num5.setText(num5);
                            binding.Num6.setText(num6);
                        }
                    }
            );
        }
        else if(getVERIFY_KEY.equals(EMAIL_KEY)) {
            binding.verifyCodeText.setText(getString(R.string.enterCode1));
            binding.Num1.setVisibility(View.GONE);
            binding.Num2.setVisibility(View.GONE);
            binding.Num3.setVisibility(View.GONE);
            binding.Num4.setVisibility(View.GONE);
            binding.Num5.setVisibility(View.GONE);
            binding.Num6.setVisibility(View.GONE);
            String email = getIntent().getExtras().getString(EMAIL_KEY);
            String password = getIntent().getExtras().getString(PASSWORD_KEY);
            myRegistrationFirebase.createEmail(email, password);
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
        String total = binding.Num1.getText().toString() + binding.Num2.getText().toString() +
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
        myRegistrationFirebase.startPhoneNumberVerification(phoneNumber,this, this);
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

    @Override
    public void setNumbers() {
        launcher.launch(Manifest.permission.READ_SMS);
    }
}
