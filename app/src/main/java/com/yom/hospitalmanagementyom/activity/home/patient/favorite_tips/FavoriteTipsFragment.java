package com.yom.hospitalmanagementyom.activity.home.patient.favorite_tips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.yom.hospitalmanagementyom.databinding.FragmentFavoriteTipsBinding;

public class FavoriteTipsFragment extends Fragment {

    private FragmentFavoriteTipsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FavoriteTipsViewModel favoriteTipsViewModel = new ViewModelProvider(this).get(FavoriteTipsViewModel.class);
        binding = FragmentFavoriteTipsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        favoriteTipsViewModel.getAllPosts().observe(getViewLifecycleOwner(), posts -> {

        });
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}