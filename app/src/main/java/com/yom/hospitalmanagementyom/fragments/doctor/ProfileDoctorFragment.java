package com.yom.hospitalmanagementyom.fragments.doctor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.registration.LoginActivity;
import com.yom.hospitalmanagementyom.databinding.FragmentProfileDoctorBinding;
import com.yom.hospitalmanagementyom.model.Constants;

import java.util.Objects;

public class ProfileDoctorFragment extends Fragment {

    FragmentProfileDoctorBinding binding;
    public ProfileDoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentProfileDoctorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.myImageProfileActivity.setOnClickListener(view1 -> {
            Intent intent=new Intent(requireContext(), LoginActivity.class);
            intent.putExtra(Constants.PROFILE_STATE, Constants.ME);
            startActivity(intent);
        });
        binding.myNameProfileActivity.setOnClickListener(view12 -> showDialog(binding.myNameProfileActivity, Constants.NAME));

        binding.myPhoneProfileActivity.setOnClickListener(view13 -> showDialog(binding.myPhoneProfileActivity, Constants.PHONE));

        binding.myTelephoneProfileActivity.setOnClickListener(view14 -> showDialog(binding.myTelephoneProfileActivity, Constants.TELEPHONE));

        binding.myEmailProfileActivity.setOnClickListener(view15 -> showDialog(binding.myEmailProfileActivity, Constants.EMAIL));

        binding.myPasswordProfileActivity.setOnClickListener(view16 -> showDialog(binding.myPasswordProfileActivity, Constants.PASSWORD));
    }

    private void showDialog(TextView textView, String Title){
        AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
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