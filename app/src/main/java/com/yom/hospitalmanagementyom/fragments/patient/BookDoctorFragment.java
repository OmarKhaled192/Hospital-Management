package com.yom.hospitalmanagementyom.fragments.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.DoctorAdapter;
import com.yom.hospitalmanagementyom.adapter.DoctorAdapter2;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentBookDoctorBinding;
import com.yom.hospitalmanagementyom.databinding.FragmentDoctorBinding;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class BookDoctorFragment extends Fragment {

    private  Repository repository ;
    private List<Doctor> doctors ;
    private DoctorAdapter2 doctorAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository=new Repository(requireContext());
        doctors =new ArrayList<>();
    }

    public BookDoctorFragment() {
        // Required empty public constructor
    }



    private FragmentBookDoctorBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookDoctorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestore.getInstance().collection(Constants.DOCTORS)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Doctor doctor = document.toObject(Doctor.class);
                        doctors.add(doctor);
                    }
                    doctorAdapter = new DoctorAdapter2(requireContext(), doctors);
                    binding.recyclerviewDoctor.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.recyclerviewDoctor.setAdapter(doctorAdapter);
                }
            }});

    }
}