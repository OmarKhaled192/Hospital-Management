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

import com.yom.hospitalmanagementyom.activity.home.patient.MessageActivity;
import com.yom.hospitalmanagementyom.adapter.ChatAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentChatBinding;
import com.yom.hospitalmanagementyom.listeners.ChatListener;
import com.yom.hospitalmanagementyom.model.Chat;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;

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
        chats = repository.getLastMessage( this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chatAdapter = new ChatAdapter(requireContext(), chats,doctors, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerviewChat.setLayoutManager(linearLayoutManager);
        binding.recyclerviewChat.setAdapter(chatAdapter);

        for(int i =0; i<chats.size(); i++){
            if(chats.get(i).getSeen().equals(Constants.NOT_OPEN)){
                repository.setLastSeenByChatId(doctors.get(i).getId(), Constants.NOT_SEEN);
            }
        }
    }

    @Override
    public void getLastMessageFinish() {
        doctors =repository.getDoctorChats(chats);
    }

    @Override
    public void onClickItem(String idChat, Doctor doctor) {
        Intent intent = new Intent(requireActivity(), MessageActivity.class);
        intent.putExtra(Constants.ID_CHAT,idChat);
        intent.putExtra(Constants.DOCTOR,doctor);

        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}