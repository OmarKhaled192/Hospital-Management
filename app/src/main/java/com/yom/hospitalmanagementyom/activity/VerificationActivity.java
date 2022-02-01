package com.yom.hospitalmanagementyom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.core.Tag;
import com.yom.hospitalmanagementyom.R;

import java.util.concurrent.TimeUnit;


public class VerificationActivity extends AppCompatActivity {

    // [START declare_auth]
    private FirebaseAuth mAuth;
    private String activityName,phoneNumber;
    // [END declare_auth]

    String total;
    private String mVerificationId;
    Intent intent;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    EditText ee1,ee2,ee3,ee4,ee5,ee6;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_verification );
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        ee1 = findViewById(R.id.ve1);
        ee2 = findViewById(R.id.ve2);
        ee3 = findViewById(R.id.ve3);
        ee4 = findViewById(R.id.ve4);
        ee5 = findViewById(R.id.ve5);
        ee6 = findViewById(R.id.ve6);

        activityName = getIntent().getExtras().getString("Activity");
       phoneNumber = getIntent().getExtras().getString("phone");
         intent = new Intent();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                //--omar-- save phone in firebase
                //signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                //  Log.w(Tag, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request

                    Toast.makeText(getApplicationContext(),"error verification1"+ e.toString(),Toast.LENGTH_LONG).show();

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded

                    Toast.makeText(getApplicationContext(),"error verification2"+ e.toString(),Toast.LENGTH_LONG).show();

                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String  verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                //Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later

              //  Toast.makeText(getApplicationContext(),"yes"+ verificationId,Toast.LENGTH_LONG).show();

                mVerificationId = verificationId;
                mResendToken = token;

            }
        };
        // [END phone_auth_callbacks]

        if(activityName.equals("RegisterForHos"))
        {

            startPhoneNumberVerification(phoneNumber);
            Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
        }

    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+2"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();

            PhoneAuthProvider.verifyPhoneNumber(options);

           // Toast.makeText(getApplicationContext(), "yes2 " + e, Toast.LENGTH_LONG).show();

    }

    public void verify(View view) {


        total = ee1.getText().toString() + ee2.getText().toString() +
                ee3.getText().toString() + ee4.getText().toString() +
                ee5.getText().toString() + ee6.getText().toString() ;

        Toast.makeText(getApplicationContext(), "total : "+total, Toast.LENGTH_LONG).show();

        if (total.length() != 6 )
            Toast.makeText(this,"you must enter code",Toast.LENGTH_LONG).show();

        else
            {

                Toast.makeText(this,"MainElse",Toast.LENGTH_LONG).show();
                if(mVerificationId.isEmpty())
                    return;
                //create a credential
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,total);
              signInUser(credential);

            }
    }
    private void signInUser(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    intent.putExtra("verify","yes");
                    setResult(RESULT_OK,intent);
                    finish();
                    Toast.makeText(getApplicationContext(),"yesIntent",Toast.LENGTH_LONG).show();
                }else {
                    intent.putExtra("verify","no");
                    setResult(RESULT_CANCELED,intent);
                    finish();
                    Toast.makeText(getApplicationContext(),"noIntent",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void resend(View view) {
        startPhoneNumberVerification(phoneNumber);
        Toast.makeText(this,getString(R.string.resend),Toast.LENGTH_LONG).show();

    }
}
