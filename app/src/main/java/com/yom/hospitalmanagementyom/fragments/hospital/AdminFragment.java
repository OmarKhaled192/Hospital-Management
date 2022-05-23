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
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentAdminBinding;
import com.yom.hospitalmanagementyom.model.Admin;
import com.yom.hospitalmanagementyom.model.Constants;

import java.util.ArrayList;
import java.util.List;


public class AdminFragment extends Fragment {

    private FragmentAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Repository repository = new Repository(requireContext());
        List<Admin> admins = repository.getAllAdmin(repository.getUser().getUid());
        List<Admin> admins1=new ArrayList<>(), admins2=new ArrayList<>();
        for (int i=0; i<admins.size();i++){
            if(admins.get(i).getWorker().equals(Constants.WORKER))
                admins1.add(admins.get(i));
            else if(admins.get(i).getWorker().equals(Constants.REQUEST))
                admins2.add(admins.get(i));
        }

        AdminAdapter adminAdapter = new AdminAdapter(requireContext(), admins1);
        binding.recyclerviewAdmin.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewAdmin.setAdapter(adminAdapter);

        AdminAdapter adminAdapter2 = new AdminAdapter(requireContext(), admins2);
        binding.recyclerviewAdmin2.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewAdmin2.setAdapter(adminAdapter2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}