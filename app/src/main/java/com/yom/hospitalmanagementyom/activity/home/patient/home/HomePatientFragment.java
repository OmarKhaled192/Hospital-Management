package com.yom.hospitalmanagementyom.activity.home.patient.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.HospitalViewAdapter;
import com.yom.hospitalmanagementyom.adapter.PostAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentHomePatientBinding;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
public class HomePatientFragment extends Fragment implements PostsListener {

    private FragmentHomePatientBinding binding;
    private Repository repository;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomePatientBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository=Repository.newInstance(requireActivity().getApplication());

        HospitalViewAdapter hospitalViewAdapter = new HospitalViewAdapter(requireContext(), repository.getHospitals(), this);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewHospitalView.setLayoutManager(linearLayoutManager2);
        binding.recyclerViewHospitalView.setAdapter(hospitalViewAdapter);

        PostAdapter postAdapter = new PostAdapter(requireContext(), repository.getPosts(this));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( requireContext() );
        binding.recyclerViewHomePosts.setLayoutManager( linearLayoutManager );
        binding.recyclerViewHomePosts.setAdapter(postAdapter);

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            showDesign(View.VISIBLE, View.GONE, View.GONE, true);
            repository.getPosts(this);
        });
    }


    private void showDesign(int shimmer, int hospital, int post, boolean isShow ){
        if(isShow){
            binding.shimmerFrameLayout.showShimmer(true);
            binding.shimmerFrameLayout.startShimmer();
        }
        else {
            binding.shimmerFrameLayout.stopShimmer();
            binding.shimmerFrameLayout.hideShimmer();
            binding.swipeRefreshLayout.setRefreshing( false );
            binding.swipeRefreshLayout.setColorSchemeColors( getResources().getColor(R.color.red) );
        }
        binding.shimmerFrameLayout.setVisibility(shimmer);
        binding.recyclerViewHospitalView.setVisibility(hospital);
        binding.recyclerViewHomePosts.setVisibility(post);
    }


    @Override
    public void finishGetPosts() {
        showDesign(View.GONE, View.VISIBLE, View.VISIBLE, false);
    }

    @Override
    public void onClickHospitalItem(int Position) {

    }

    @Override
    public void onClickLikePost(int Position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}