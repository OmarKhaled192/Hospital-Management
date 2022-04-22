package com.yom.hospitalmanagementyom.activity.home.doctor;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.databinding.FragmentCreatePostBinding;


public class CreatePostFragment extends Fragment {

    private FragmentCreatePostBinding binding;
    private boolean isCheck;


    public CreatePostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCheck=true;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.edit.setOnClickListener(v -> {
            if(isCheck)
                show();
            else
                hide();
        });


    }

    private void show() {
        isCheck=false;

        binding.camera.setVisibility(View.VISIBLE);
        binding.gallery.setVisibility(View.VISIBLE);
        binding.video.setVisibility(View.VISIBLE);

        //animation
        binding.edit.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_open));
        binding.camera.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom));
        binding.gallery.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom));
        binding.video.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom));

    }

    private void hide() {
        isCheck=true;
        binding.camera.setVisibility(View.GONE);
        binding.gallery.setVisibility(View.GONE);
        binding.video.setVisibility(View.GONE);

        //animation
        binding.edit.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_close));
        binding.camera.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom));
        binding.gallery.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom));
        binding.video.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom));
    }
}