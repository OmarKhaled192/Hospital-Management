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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.AdminAdapter;
import com.yom.hospitalmanagementyom.adapter.DoctorAdapter;
import com.yom.hospitalmanagementyom.adapter.DrugsAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentAdminBinding;
import com.yom.hospitalmanagementyom.model.Admin;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Drug;

import java.util.ArrayList;
import java.util.List;


public class AdminFragment extends Fragment {

    private FragmentAdminBinding binding;
    private Repository repository ;
    private List<Admin> admins ;
    private AdminAdapter adminAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new Repository(requireContext());
        admins=new ArrayList<>();
        //doctors = repository.getAllDoctors(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestore.getInstance().collection(Constants.ADMINS)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Admin admin = document.toObject(Admin.class);
                        admins.add(admin);
                    }
                    adminAdapter = new AdminAdapter(requireContext(), admins);
                    binding.recyclerviewAdmin.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.recyclerviewAdmin.setAdapter(adminAdapter);
                }
            }});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}