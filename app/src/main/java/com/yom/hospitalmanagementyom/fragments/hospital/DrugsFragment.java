package com.yom.hospitalmanagementyom.fragments.hospital;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yom.hospitalmanagementyom.adapter.DoctorAdapter;
import com.yom.hospitalmanagementyom.adapter.DrugsAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentDrugsBinding;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Drug;

import java.util.ArrayList;
import java.util.List;

public class DrugsFragment extends Fragment {

    private FragmentDrugsBinding binding;
    private  Repository repository ;
    private List<Drug> drugs ;
    private DrugsAdapter drugsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new Repository(requireContext());
        drugs=new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDrugsBinding.inflate(inflater, container, false);
       return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseFirestore.getInstance().collection(Constants.DRUGS)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Drug drug = document.toObject(Drug.class);
                            drugs.add(drug);
                        }
                        drugsAdapter = new DrugsAdapter(requireContext(), drugs);
                        binding.recyclerviewDrug.setLayoutManager(new LinearLayoutManager(requireContext()));
                        binding.recyclerviewDrug.setAdapter(drugsAdapter);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}