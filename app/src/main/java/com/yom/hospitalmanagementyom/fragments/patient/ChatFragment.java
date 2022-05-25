package com.yom.hospitalmanagementyom.fragments.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.firebase.database.FirebaseDatabase;
import com.yom.hospitalmanagementyom.activity.home.patient.MessageActivity;
import com.yom.hospitalmanagementyom.adapter.ChatAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentChatBinding;
import com.yom.hospitalmanagementyom.listeners.ChatListener;
import com.yom.hospitalmanagementyom.model.Chat;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment implements ChatListener {
    private FragmentChatBinding binding;
    private ChatAdapter chatAdapter;
    private Repository repository;
    private List<Chat> chats;
    private List<Doctor> doctors;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new Repository(requireContext());
        //chats = repository.getLastMessage( this);
        doctors = new ArrayList<>();
        chats =new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Chat chat = new Chat();
        chat.setId(FirebaseDatabase.getInstance().getReference("Chats").push().getKey());
        chat.setMessage("Hi");
        chat.setSeen("Seen");
        chat.setTime("10:00 PM");
        chat.setIdSender("1");
        chat.setDelete("");
        chat.setNameChat("Mohamed Ahmed");
        chat.setProfileChat("ff");
        chat.setIdReceiver(repository.getUser().getUid());
        //repository.SendMessage(chat);

        Chat chat2 = new Chat();
        chat2.setMessage("كيف الحال");
        chat2.setSeen("Not Open");
        chat2.setTime("10:00 PM");
        chat2.setIdSender("1223");
        chats.add(chat);
        chats.add(chat2);
        Doctor doctor=new Doctor();
        doctor.setName("Mohamed Ahmed");
        doctor.setProfile("hy");
        doctors.add(doctor);
        Doctor doctor2=new Doctor();
        doctor2.setName("Ahmed");
        doctor2.setProfile("hy");

        doctors.add(doctor2);

        chatAdapter = new ChatAdapter(requireContext(), chats,doctors, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerviewChat.setLayoutManager(linearLayoutManager);
        binding.recyclerviewChat.setAdapter(chatAdapter);

//        for(int i =0; i<chats.size(); i++){
//            if(chats.get(i).getSeen().equals(Constants.NOT_OPEN)){
//                repository.setLastSeenByChatId(doctors.get(i).getId(), Constants.NOT_SEEN);
//            }
//        }
    }

    @Override
    public void getLastMessageFinish() {
        //doctors =repository.getDoctorChats(chats);
        //Toast.makeText(getContext(),"Hi",Toast.LENGTH_LONG).show();
        for (int i=0; i<chats.size();i++)
            chatAdapter.notifyItemChanged(i);
    }

    @Override
    public void onClickItem(String idChat, String idReceiver) {
        Intent intent = new Intent(requireActivity(), MessageActivity.class);
        intent.putExtra(Constants.ID_CHAT,idChat);
        intent.putExtra(Constants.DOCTOR,idReceiver);

        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}