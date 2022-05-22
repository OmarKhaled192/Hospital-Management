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
import com.yom.hospitalmanagementyom.adapter.DrugsAdapter;
import com.yom.hospitalmanagementyom.databinding.FragmentDrugsBinding;
import com.yom.hospitalmanagementyom.model.Admin;
import com.yom.hospitalmanagementyom.model.Drug;

import java.util.ArrayList;
import java.util.List;

public class DrugsFragment extends Fragment {
    private RecyclerView recyclerviewdrug;
    private DrugsAdapter drugsAdapter;
    private List<Drug> drugs;
    private FragmentDrugsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDrugsBinding.inflate(inflater, container, false);
       return  binding.getRoot();



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerviewdrug=view.findViewById(R.id.recyclerviewdrug);
        Drug drug=new Drug();
        drug.setNameDrug("drug");
       drugs=new ArrayList<>();
        for (int i=0;i<5;i++)
            drugs.add(drug);

        drugsAdapter=new DrugsAdapter(requireContext(),drugs);
        recyclerviewdrug.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerviewdrug.setAdapter(drugsAdapter);
    }
}