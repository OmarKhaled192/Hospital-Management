package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.MessageAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivityMessageBinding;
import com.yom.hospitalmanagementyom.model.Chat;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private ActivityMessageBinding binding;
    private MessageAdapter messageAdapter;
    private Repository repository;
    private String idChat;
    private List<Chat> chats =new ArrayList<>();
    private Doctor doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        doctor = (Doctor) getIntent().getSerializableExtra(Constants.DOCTOR);
        idChat = getIntent().getExtras().getString(Constants.ID_CHAT);
        repository = new Repository(this);
        chats =new ArrayList<>();
        chats = repository.getMessages(idChat);

        messageAdapter = new MessageAdapter(this, chats);
        binding.recyclerViewMessage.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewMessage.setAdapter(messageAdapter);

        init();
    }

    private void writeNow(CharSequence charSequence) {
        if(charSequence.length()==0){
            repository.setWriteNow(chats.get(chats.size()-1).getTime(), doctor.getId(), idChat);
        }else if(charSequence.length()!=0){
            repository.setWriteNow(getString(R.string.timeNow), doctor.getId(), idChat);
        }
    }

    private void sendMessage(String message) {
        Chat chat=new Chat();
        chat.setId(repository.getUser().getUid()+"_"+doctor.getId());
        chat.setIdSender(repository.getUser().getUid());
        chat.setIdReceiver(doctor.getId());
        chat.setMessage(message);
        chat.setTime(repository.getTimeNow());
        chat.setSeen(Constants.NOT_OPEN);
        chat.setDelete(Constants.NO);
        chat.setNumberOfMessageNotSeen(chats.get(chats.size()-1).getNumberOfMessageNotSeen()+1);
        repository.SendMessage(chat);
    }

    void init(){
        repository.setStatus(Constants.PATIENT, Constants.ONLINE);
        repository.setLastSeenByChatId(doctor.getId(), Constants.SEEN);
        Picasso.with(this).load(doctor.getProfile()).error(R.drawable.profile).into(binding.imageUserMessage);
        binding.friendNameMessage.setText(doctor.getName());
        if(doctor.getStatus().equals(Constants.ONLINE))
            binding.statusFriend.setText(R.string.online);
        if(doctor.getStatus().equals(Constants.OFFLINE))
            binding.statusFriend.setText(R.string.offline);


        binding.message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                writeNow(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.message.getText().toString().equals(""))
                    sendMessage(binding.message.getText().toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repository.setStatus(Constants.PATIENT, Constants.OFFLINE);
    }
}