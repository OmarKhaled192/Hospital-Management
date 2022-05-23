package com.yom.hospitalmanagementyom.fragments.hospital;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.yom.hospitalmanagementyom.adapter.DrugsAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentDrugsBinding;
import com.yom.hospitalmanagementyom.model.Drug;
import java.util.List;

public class DrugsFragment extends Fragment {
    private FragmentDrugsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDrugsBinding.inflate(inflater, container, false);
       return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Repository repository = new Repository(requireContext());
        List<Drug> drugs = repository.getAllDrugs(repository.getUser().getUid());
        DrugsAdapter drugsAdapter = new DrugsAdapter(requireContext(), drugs);
        binding.recyclerviewdrug.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewdrug.setAdapter(drugsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}