package com.yom.hospitalmanagementyom.fragments.hospital;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.AdminAdapter;
import com.yom.hospitalmanagementyom.adapter.DoctorAdapter;
import com.yom.hospitalmanagementyom.databinding.FragmentAdminBinding;
import com.yom.hospitalmanagementyom.model.Admin;
import com.yom.hospitalmanagementyom.model.Doctor;

import java.util.ArrayList;
import java.util.List;


public class AdminFragment extends Fragment {

    private RecyclerView recyclerviewadmin,recyclerviewadmin2;
    private AdminAdapter adminAdapter;
    private List<Admin> admins;

    private FragmentAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerviewadmin=view.findViewById(R.id.recyclerviewAdmin);
        recyclerviewadmin2=view.findViewById(R.id.recyclerviewAdmin2);
       Admin admin=new Admin();
        admin.setName("admin");
        admins=new ArrayList<>();
        for (int i=0;i<5;i++)
            admins.add(admin);

        adminAdapter=new AdminAdapter(requireContext(),admins);
        recyclerviewadmin.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerviewadmin.setAdapter(adminAdapter);
        recyclerviewadmin2.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerviewadmin2.setAdapter(adminAdapter);
    }
}