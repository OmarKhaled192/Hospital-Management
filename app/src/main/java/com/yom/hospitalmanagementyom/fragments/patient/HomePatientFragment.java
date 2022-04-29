package com.yom.hospitalmanagementyom.fragments.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.registration.MainActivity;
import com.yom.hospitalmanagementyom.adapter.HospitalViewAdapter;
import com.yom.hospitalmanagementyom.adapter.PostAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentHomePatientBinding;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.List;

public class HomePatientFragment extends Fragment implements PostsListener {

    private FragmentHomePatientBinding binding;
    private Repository repository;
    private List<Hospital> hospitals;
    private List<Post> posts;
    private List<Doctor> doctors;
    private PostAdapter postAdapter;

    public HomePatientFragment(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository=new Repository(requireContext());
        hospitals = repository.getHospitals();
        posts = repository.getPosts(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomePatientBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        HospitalViewAdapter hospitalViewAdapter = new HospitalViewAdapter(requireContext(), hospitals, this);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewHospitalView.setLayoutManager(linearLayoutManager2);
        binding.recyclerViewHospitalView.setAdapter(hospitalViewAdapter);

        postAdapter = new PostAdapter(requireContext(), posts, doctors, this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( requireContext() );
        binding.recyclerViewHomePosts.setLayoutManager( linearLayoutManager );
        binding.recyclerViewHomePosts.setAdapter(postAdapter);

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            showDesign(View.VISIBLE, View.GONE, View.GONE, true);
            hospitals = repository.getHospitals();
            posts = repository.getPosts(this);
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
            binding.swipeRefreshLayout.setColorSchemeColors( getResources().getColor(R.color.teal_700) );
        }
        binding.shimmerFrameLayout.setVisibility(shimmer);
        binding.recyclerViewHospitalView.setVisibility(hospital);
        binding.recyclerViewHomePosts.setVisibility(post);
    }


    @Override
    public void finishGetPosts() {
        doctors = repository.getDoctorPosts(posts,this);
    }

    @Override
    public void finishGetDoctors() {
        showDesign(View.GONE, View.VISIBLE, View.VISIBLE, false);
    }

    @Override
    public void onClickHospitalItem(int Position) {
        Intent intent =new Intent(getActivity(), MainActivity.class);
        intent.putExtra(Constants.HOSPITAL, hospitals.get(Position).getId());
        startActivity(intent);
    }

    @Override
    public void onClickLikePost(int Position, String postId) {
        repository.setInteractWithPost(postId,Constants.LIKES,repository.getUser().getUid());
        repository.deleteInteractWithPost(postId,Constants.DISLIKES,repository.getUser().getUid());
        postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onCancelLikePost(int Position, String postId) {
        repository.setInteractWithPost(postId,Constants.LIKES,repository.getUser().getUid());
        postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onClickDisLikePost(int Position, String postId) {
        repository.setInteractWithPost(postId,Constants.DISLIKES,repository.getUser().getUid());
        repository.deleteInteractWithPost(postId,Constants.LIKES,repository.getUser().getUid());
        postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onCancelDisLikePost(int Position, String postId) {
        repository.setInteractWithPost(postId,Constants.DISLIKES,repository.getUser().getUid());
        postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onClickStarPost(int Position, String postId) {
        repository.setInteractWithPost(postId,Constants.STARS,repository.getUser().getUid());
        postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onCancelStarPost(int Position, String postId) {
        repository.setInteractWithPost(postId,Constants.STARS,repository.getUser().getUid());
        postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}