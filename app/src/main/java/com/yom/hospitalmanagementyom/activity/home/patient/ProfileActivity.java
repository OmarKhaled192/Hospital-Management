package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.registration.LoginActivity;
import com.yom.hospitalmanagementyom.activity.registration.MainActivity;
import com.yom.hospitalmanagementyom.databinding.ActivityProfileBinding;
import com.yom.hospitalmanagementyom.model.Constants;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;

    private String State;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.myImageProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this, LoginActivity.class);
                intent.putExtra(Constants.PROFILE_STATE, Constants.ME);
                startActivity(intent);
            }
        });
        binding.myNameProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(binding.myNameProfileActivity, Constants.NAME);
            }
        });

        binding.myPhoneProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(binding.myPhoneProfileActivity, Constants.PHONE);
            }
        });
        binding.myTelephoneProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(binding.myTelephoneProfileActivity, Constants.TELEPHONE);
            }
        });
        binding.myEmailProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(binding.myEmailProfileActivity, Constants.EMAIL);
            }
        });
        binding.myPasswordProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(binding.myPasswordProfileActivity, Constants.PASSWORD);
            }
        });
    }

    private void showDialog(TextView textView, String Title){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.item_edit_profile,null);
        TextInputLayout textInputLayout=view.findViewById(R.id.boxProfile);
        textInputLayout.setHint(Title);
        switch (Title) {
            case Constants.NAME:
                textInputLayout.setStartIconDrawable(R.drawable.person);
                break;
            case Constants.PHONE:
                textInputLayout.setStartIconDrawable(R.drawable.phone);
                break;
            case Constants.TELEPHONE:
                textInputLayout.setStartIconDrawable(R.drawable.telephone);
                break;
            case Constants.EMAIL:
                textInputLayout.setStartIconDrawable(R.drawable.email);
                break;
            case Constants.PASSWORD:
                textInputLayout.setStartIconDrawable(R.drawable.lock);
                break;
        }

        TextInputEditText textInputEditText=view.findViewById(R.id.editProfile);
        textInputEditText.setText(textView.getText());
        builder.setView(view);
        builder.setPositiveButton(getString(R.string.save), (dialogInterface, i) -> {
            textView.setText(Objects.requireNonNull(textInputEditText.getText()).toString());
            dialogInterface.dismiss();
        });
        builder.setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create();
        builder.show();
    }
}