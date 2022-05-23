package com.yom.hospitalmanagementyom.fragments.doctor;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.PostAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentHomeDoctorBinding;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.ArrayList;
import java.util.List;

public class HomeDoctorFragment extends Fragment implements PostsListener {

    private FragmentHomeDoctorBinding binding;
    private List<Post> posts;
    private List<Doctor> doctors;
    private Repository repository;
    private PostAdapter postAdapter;
    public HomeDoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository=new Repository(requireContext());
        repository =new Repository(requireContext());
        posts = repository.getPosts(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeDoctorBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postAdapter = new PostAdapter(requireContext(), posts, doctors, this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( requireContext() );
        binding.recyclerviewMyPosts.setLayoutManager( linearLayoutManager );
        binding.recyclerviewMyPosts.setAdapter(postAdapter);

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            showDesign(View.VISIBLE, View.GONE, true);
            posts = repository.getPosts(this);
        });
    }


    private void showDesign(int shimmer,  int post, boolean isShow ){
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
        binding.recyclerviewMyPosts.setVisibility(post);
    }


    @Override
    public void finishGetPosts() {
        List<Post>posts1=new ArrayList<>();
        for (int i=0; i<posts.size();i++){
            if(posts.get(i).getIdDoctor().equals(repository.getUser().getUid()))
                posts1.add(posts.get(i));
        }
        doctors = repository.getDoctorPosts(posts1,this);
    }

    @Override
    public void finishGetDoctors() {
        showDesign(View.GONE, View.VISIBLE, false);
    }

    @Override
    public void onClickHospitalItem(int Position) {
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


}