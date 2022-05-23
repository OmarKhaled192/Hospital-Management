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
import com.yom.hospitalmanagementyom.adapter.AdminAdapter;
import com.yom.hospitalmanagementyom.adapter.DoctorAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentDoctorBinding;
import com.yom.hospitalmanagementyom.model.Admin;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorFragment extends Fragment {

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
        Repository repository = new Repository(requireContext());
        List<Doctor> doctors = repository.getAllDoctors(repository.getUser().getUid());
        List<Doctor> doctors1=new ArrayList<>(), doctors2=new ArrayList<>();
        for (int i=0; i<doctors.size();i++){
            if(doctors.get(i).getWorker().equals(Constants.WORKER))
                doctors1.add(doctors.get(i));
            else if(doctors.get(i).getWorker().equals(Constants.REQUEST))
                doctors2.add(doctors.get(i));
        }

        DoctorAdapter doctorAdapter = new DoctorAdapter(requireContext(), doctors1);
        binding.recyclerviewDoctor.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewDoctor.setAdapter(doctorAdapter);

        DoctorAdapter doctorAdapter1 = new DoctorAdapter(requireContext(), doctors2);
        binding.recyclerviewDoctor2.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewDoctor2.setAdapter(doctorAdapter1);
    }
}