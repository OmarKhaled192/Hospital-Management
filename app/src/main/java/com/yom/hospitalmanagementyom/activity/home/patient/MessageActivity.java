package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.yom.hospitalmanagementyom.adapter.MessageAdapter;
import com.yom.hospitalmanagementyom.databinding.ActivityMessageBinding;
import com.yom.hospitalmanagementyom.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private ActivityMessageBinding binding;
    private MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<Chat> chats=new ArrayList<>();

        Chat chat= new Chat();
        Chat chat2= new Chat();
        chat.setMessage("Hi");
        chat.setIdSender("1");
        chat.setTime("10:05 AM");
        chat.setSeen("Seen");
        chats.add(chat);
        chat2.setMessage("Hello");
        chat2.setTime("10:45 AM");
        chat2.setIdSender("2");
        chats.add(chat2);

        messageAdapter = new MessageAdapter(this, chats);
        binding.recyclerViewMessage.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewMessage.setAdapter(messageAdapter);
    }
}