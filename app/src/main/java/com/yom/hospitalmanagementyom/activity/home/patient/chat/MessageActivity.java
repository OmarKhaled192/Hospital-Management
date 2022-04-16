package com.yom.hospitalmanagementyom.activity.home.patient.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yom.hospitalmanagementyom.databinding.ActivityMessageBinding;

public class MessageActivity extends AppCompatActivity {

    private ActivityMessageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}