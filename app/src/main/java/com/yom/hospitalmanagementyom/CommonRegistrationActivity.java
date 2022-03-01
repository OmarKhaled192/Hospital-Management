package com.yom.hospitalmanagementyom.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.databinding.ActivityRegistrationCommonBinding;
import com.yom.hospitalmanagementyom.java.Hospital;
import com.yom.hospitalmanagementyom.java.MyRegistrationFirebase;
import com.yom.hospitalmanagementyom.java.Patient;
import com.yom.hospitalmanagementyom.listeners.SaveDataListener;

public class CommonRegistrationActivity extends AppCompatActivity implements SaveDataListener {

  private ActivityRegistrationCommonBinding binding;
  private Patient patient;
  private Hospital hospital;
  private final String PATIENT_KEY="Patient";
  private final String HOSPITAL_KEY="Hospital";
  private final String ACTIVITY_KEY="Activity";
  private final String PHONE_KEY="Phone";
  private final String EMAIL_KEY="Email";
  private final String PASSWORD_KEY="Password";
  private final String VERIFY_KEY="Verify";
  private ActivityResultLauncher<Intent> activityResultLauncherPhone,activityResultLauncherEmail;
  private String password;
  private MyRegistrationFirebase myRegistrationFirebase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding= ActivityRegistrationCommonBinding.inflate(getLayoutInflater());
    setContentView( binding.getRoot() );
    setSupportActionBar(binding.toolbarCommonRegistrationActivity);
    binding.toolbarCommonRegistrationActivity.setNavigationIcon(R.drawable.back);
    binding.toolbarCommonRegistrationActivity.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });

    patient=new Patient();
    hospital=new Hospital();
    myRegistrationFirebase= MyRegistrationFirebase.getInstance(this);
    checkPatientOrHospital();

    activityResultLauncherPhone=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
              @Override
              public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()==RESULT_OK) {
                  //handling(true,false,true,false,true,true,true,true,true,false);
                  //binding.VerifyEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                  handling21(View.VISIBLE,false,View.VISIBLE,false,R.drawable.done,View.VISIBLE,true,View.VISIBLE,true,0,View.GONE,false);
                }
                else if(result.getResultCode()==RESULT_CANCELED ) {
                  //handling(true,true,true,true,false,false,false,false,false,false);
                  //binding.VerifyEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                  handling21(View.VISIBLE,true,View.VISIBLE,true,R.drawable.close,View.GONE,false,View.GONE,false,0,View.GONE,false);
                }
              }
            }
    );

    activityResultLauncherEmail=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
              @Override
              public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()==RESULT_OK) {
                 // handling(true,false,true,false,true,true,false,true,false,true);
                  //binding.VerifyEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_done_24,0);
                  handling21(View.VISIBLE,false,View.VISIBLE,false,R.drawable.done,View.VISIBLE,false,View.VISIBLE,false,R.drawable.done,View.VISIBLE,true);

                }
                else if(result.getResultCode()==RESULT_CANCELED ) {
                  //handling(true,false,true,false,true,true,true,true,true,false);
                  //binding.VerifyEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_close_24,0);
                  handling21(View.VISIBLE,false,View.VISIBLE,false,R.drawable.done,View.VISIBLE,true,View.VISIBLE,true,R.drawable.close,View.GONE,false);

                }
              }
            }
    );

  }

  private void checkPatientOrHospital(){
    if(getIntent().getExtras().getString(ACTIVITY_KEY).equals(PATIENT_KEY)) {
      patient = (Patient) getIntent().getExtras().get(PATIENT_KEY);
      password=patient.getPassword();
    }
    else if(getIntent().getExtras().getString(ACTIVITY_KEY).equals(HOSPITAL_KEY)) {
      hospital = (Hospital) getIntent().getExtras().get(HOSPITAL_KEY);
      password="12345678";
    }
  }

  private void handling21(int PV, boolean PE, int VPV, boolean VPE, int PVI, int EV, boolean EE, int VEV,boolean VEE, int VEI, int SV, boolean SE){
    binding.Phone.setVisibility(PV);
    binding.Phone.setEnabled(PE);
    binding.VerifyPhone.setVisibility(VPV);
    binding.VerifyPhone.setEnabled(VPE);
    binding.VerifyPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,PVI,0);
    binding.Email.setVisibility(EV);
    binding.Email.setEnabled(EE);
    binding.VerifyEmail.setVisibility(VEV);
    binding.VerifyEmail.setEnabled(VEE);
    binding.VerifyEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,VEI,0);
    binding.signUp.setVisibility(SV);
    binding.signUp.setEnabled(SE);
  }

  private void handling(boolean... check){
    //Phone text visibility
    if(check[0]==true)
      binding.Phone.setVisibility(View.VISIBLE);
    else
      binding.Phone.setVisibility(View.GONE);

    //Phone text enable
    if(check[1]==true)
      binding.Phone.setEnabled(true);
    else
      binding.Phone.setEnabled(false);

    //Phone Btn visibility
    if(check[2]==true)
      binding.VerifyPhone.setVisibility(View.VISIBLE);
    else
      binding.VerifyPhone.setVisibility(View.GONE);

    // Phone Btn enable
    if(check[3]==true)
      binding.VerifyPhone.setEnabled(true);
    else
      binding.VerifyPhone.setEnabled(false);

    //Phone Btn photo
    if(check[4]==true)
      binding.VerifyPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.done,0);
    else
      binding.VerifyPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.close,0);

    //Email text visibility
    if(check[5]==true)
      binding.Email.setVisibility(View.VISIBLE);
    else
      binding.Email.setVisibility(View.GONE);

    //Email text enable
    if(check[6]==true)
      binding.Email.setEnabled(true);
    else
      binding.Email.setEnabled(false);

    //Email Btn visibility
    if(check[7]==true)
      binding.VerifyEmail.setVisibility(View.VISIBLE);
    else
      binding.VerifyEmail.setVisibility(View.GONE);

    // Email Btn enable
    if(check[8]==true)
      binding.VerifyEmail.setEnabled(true);
    else
      binding.VerifyEmail.setEnabled(false);

    //Register Btn
    if(check[9]==true)
      binding.signUp.setVisibility(View.VISIBLE);
    else
      binding.signUp.setVisibility(View.GONE);

  }

  public void verifyPhone(View view) {
    if (binding.Phone.length() == 0)
      binding.box1.setError( getString(R.string.phone) );
    else {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(R.string.phone).setMessage(getString(R.string.questionChangePhone) + binding.Phone.getText().toString() + getString(R.string.questionTag))
              .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  Intent intent = new Intent(getBaseContext(), VerificationActivity.class);
                  intent.putExtra(PHONE_KEY, binding.Phone.getText().toString());
                  intent.putExtra(VERIFY_KEY,PHONE_KEY);
                  activityResultLauncherPhone.launch(intent);
                }
              });
      builder.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          dialog.dismiss();
          binding.Phone.setText("");
        }
      });
      builder.show();
    }
  }

  public void verifyEmail(View view) {
    if (binding.Email.length() == 0)
      binding.box2.setError( getString(R.string.Email) );
    else {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(R.string.Email).setMessage(getString(R.string.questionChangeEmail) + binding.Email.getText().toString() + getString(R.string.questionTag))
              .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  Intent intent = new Intent(getBaseContext(), VerificationActivity.class);
                  intent.putExtra(EMAIL_KEY, binding.Email.getText().toString());
                  intent.putExtra(PASSWORD_KEY,password);
                  intent.putExtra(VERIFY_KEY,EMAIL_KEY);
                  activityResultLauncherEmail.launch(intent);
                }
              });
      builder.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          dialog.dismiss();
          binding.Email.setText("");
        }
      });
      builder.show();
    }
  }

  public void saveData(View view) {
    if (getIntent().getExtras().getString(ACTIVITY_KEY).equals(PATIENT_KEY)) {
      patient.setPhone(binding.Phone.getText().toString());
      patient.setEmail(binding.Email.getText().toString());
      myRegistrationFirebase.savePatient(patient,this);
    }
    if (getIntent().getExtras().getString(ACTIVITY_KEY).equals(HOSPITAL_KEY)) {
      hospital.setPhone(binding.Phone.getText().toString());
      hospital.setEmail(binding.Email.getText().toString());
      myRegistrationFirebase.saveHospital(hospital,this);
    }
  }

  @Override
  public void successSavePatient() {
    Intent intent=new Intent(this,HomeActivityForPatient.class);
    startActivity(intent);
    finishAffinity();
  }

  @Override
  public void successSaveHospital() {
    Intent intent=new Intent(this,HomeActivityForPatient.class);
    startActivity(intent);
    finishAffinity();
  }
}