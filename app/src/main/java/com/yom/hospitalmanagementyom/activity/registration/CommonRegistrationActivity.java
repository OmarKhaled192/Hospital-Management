package com.yom.hospitalmanagementyom.activity.registration;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.patient.HomePatientActivity;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivityRegistrationCommonBinding;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Patient;
import com.yom.hospitalmanagementyom.listeners.SaveDataListener;
import java.util.Objects;

public class CommonRegistrationActivity extends AppCompatActivity implements SaveDataListener {

  private ActivityRegistrationCommonBinding binding;
  private Patient patient;
  private Hospital hospital;
  private ActivityResultLauncher<Intent> activityResultLauncherPhone,activityResultLauncherEmail;
  private String password, phone, email;
  private Repository repository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding= ActivityRegistrationCommonBinding.inflate(getLayoutInflater());
    setContentView( binding.getRoot() );

    checkPatientOrHospital();

    activityResultLauncherPhone=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
              if(result.getResultCode()==RESULT_OK) {
                handling(View.VISIBLE, R.drawable.email, getString(R.string.Email), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, getString(R.string.verify));
              }
              else if(result.getResultCode()==RESULT_CANCELED ) {
                handling(View.VISIBLE, R.drawable.phone, getString(R.string.phone), InputType.TYPE_CLASS_PHONE, getString(R.string.verify));
              }
            }
    );

    activityResultLauncherEmail=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
              if(result.getResultCode()==RESULT_OK) {
                handling(View.GONE, R.drawable.email, getString(R.string.Email), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, getString(R.string.verify));
              }
              else if(result.getResultCode()==RESULT_CANCELED ) {
                handling(View.VISIBLE, R.drawable.email, getString(R.string.Email), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, getString(R.string.verify));
              }
            }
    );

    binding.Verify.setOnClickListener(view -> verify());


    patient=new Patient();
    hospital=new Hospital();
    repository = new Repository(getApplicationContext());

  }

  private void checkPatientOrHospital(){
    if(getIntent().getExtras().getString(Constants.ACTIVITY).equals(Constants.PATIENT)) {
      patient = (Patient) getIntent().getExtras().get(Constants.PATIENT);
      password=patient.getPassword();
    }
    else if(getIntent().getExtras().getString(Constants.ACTIVITY).equals(Constants.HOSPITAL)) {
      hospital = (Hospital) getIntent().getExtras().get(Constants.HOSPITAL);
      password="1234";
    }
  }

  private void verify() {
    if(binding.editText.getVisibility() == View.VISIBLE) {
      if (binding.editText.getInputType() == InputType.TYPE_CLASS_PHONE)
        verifyPhone();
      else
        verifyEmail();
    } else
      saveData();
  }

  private void verifyPhone() {
    if (binding.editText.length() == 0)
      binding.box1.setError( getString(R.string.phoneError) );
    else {
      phone = Objects.requireNonNull(binding.editText.getText()).toString();
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(R.string.phone).setMessage(getString(R.string.questionChangePhone) + " " + phone + getString(R.string.questionTag))
              .setNegativeButton(R.string.yes, (dialog, which) -> {
                Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                intent.putExtra(Constants.PHONE, phone);
                intent.putExtra(Constants.VERIFY,Constants.PHONE);
                activityResultLauncherPhone.launch(intent);
              });
      builder.setPositiveButton(R.string.no, (dialog, which) -> {
        dialog.dismiss();
        binding.editText.setText("");
      });
      builder.show();
    }
  }

  private void verifyEmail() {
    if (binding.editText.length() == 0)
      binding.box1.setError( getString(R.string.emailError) );
    else {
      email = Objects.requireNonNull(binding.editText.getText()).toString();
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(R.string.Email).setMessage(getString(R.string.questionChangeEmail) + " " + email + getString(R.string.questionTag))
              .setNegativeButton(R.string.yes, (dialog, which) -> {
                Intent intent = new Intent(getBaseContext(), VerificationActivity.class);
                intent.putExtra(Constants.EMAIL, email);
                intent.putExtra(Constants.PASSWORD,password);
                intent.putExtra(Constants.VERIFY,Constants.EMAIL);
                activityResultLauncherEmail.launch(intent);
              });
      builder.setPositiveButton(R.string.no, (dialog, which) -> {
        dialog.dismiss();
        binding.editText.setText("");
      });
      builder.show();
    }
  }

  private void handling(int boxVisible, int drawable, String boxHint, int typeInput, String buttonText){
    binding.box1.setVisibility(boxVisible);
    binding.box1.setStartIconDrawable(drawable);
    binding.box1.setHint(boxHint);
    binding.editText.setInputType(typeInput);
    binding.Verify.setText(buttonText);
  }

  private void saveData() {
    if (getIntent().getExtras().getString(Constants.ACTIVITY).equals(Constants.PATIENT)) {
      patient.setPhone(phone);
      patient.setEmail(email);
      repository.savePatient(patient,this);
    }
    if (getIntent().getExtras().getString(Constants.ACTIVITY).equals(Constants.HOSPITAL)) {
      hospital.setPhone(phone);
      hospital.setEmail(email);
      repository.saveHospital(hospital,this);
    }
  }

  @Override
  public void successSavePatient() {
    Intent intent=new Intent(this, HomePatientActivity.class);
    startActivity(intent);
    finishAffinity();
    TastyToast.makeText(getApplicationContext(),getString(R.string.successSignUp),TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
  }

  @Override
  public void failSavePatient() {
    TastyToast.makeText(getApplicationContext(),getString(R.string.failSignIn),TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
  }

  @Override
  public void successSaveHospital() {
    Intent intent=new Intent(this,HomePatientActivity.class);
    startActivity(intent);
    finishAffinity();
    TastyToast.makeText(getApplicationContext(),getString(R.string.successSignUp),TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
  }

  @Override
  public void failSaveHospital() {
    TastyToast.makeText(getApplicationContext(),getString(R.string.failSignIn),TastyToast.LENGTH_LONG,TastyToast.ERROR).show();

  }
}