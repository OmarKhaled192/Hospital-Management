package com.yom.hospitalmanagementyom.fragments.hospital;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.DoctorAdapter;
import com.yom.hospitalmanagementyom.databinding.FragmentDoctorBinding;
import com.yom.hospitalmanagementyom.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorFragment extends Fragment {
    private RecyclerView recyclerviewdoctor,recyclerviewdoctor2;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctors;
    private FragmentDoctorBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDoctorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerviewdoctor=view.findViewById(R.id.recyclerviewDoctor);
        recyclerviewdoctor2=view.findViewById(R.id.recyclerviewDoctor2);
        Doctor doctor=new Doctor();
        doctor.setName("doctor");
        doctors=new ArrayList<>();
        for (int i=0;i<5;i++)
            doctors.add(doctor);

        doctorAdapter=new DoctorAdapter(requireContext(), doctors);
        recyclerviewdoctor.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerviewdoctor.setAdapter(doctorAdapter);
        recyclerviewdoctor2.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerviewdoctor2.setAdapter(doctorAdapter);
    }
}