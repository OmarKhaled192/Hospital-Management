package com.yom.hospitalmanagementyom.fragments.hospital;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.yom.hospitalmanagementyom.databinding.FragmentDrugsBinding;

public class DrugsFragment extends Fragment {

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
}