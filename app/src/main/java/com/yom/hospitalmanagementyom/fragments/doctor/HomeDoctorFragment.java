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

        postAdapter = new PostAdapter(requireContext(), posts, this);
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
            binding.swipeRefreshLayout.setColorScheme( R.color.teal_700 );
        }
        binding.shimmerFrameLayout.setVisibility(shimmer);
        binding.recyclerviewMyPosts.setVisibility(post);
    }


    @Override
    public void finishGetPosts() {
        showDesign(View.GONE, View.VISIBLE, false);
    }

    @Override
    public void finishGetDoctors() {
    }

    @Override
    public void onClickHospitalItem(int Position) {
    }

    @Override
    public void onClickLikePost(Post post, int Position) {
        List<String> strings=post.getLikes();
        strings.add(repository.getUser().getUid());
        post.setLikes(strings);

        List<String> strings2=post.getDisLikes();
        for(int i=0; i<strings2.size(); i++){
            if(strings2.get(i).equals(repository.getUser().getUid())) {
                strings2.remove(i);
                return;
            }
        }
        post.setDisLikes(strings2);
        repository.setInteractWithPost(post);
        postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onCancelLikePost(Post post, int Position) {
        List<String> strings2=post.getLikes();
        for(int i=0; i<strings2.size(); i++){
            if(strings2.get(i).equals(repository.getUser().getUid())) {
                strings2.remove(i);
                return;
            }
        }
        post.setDisLikes(strings2);
        repository.setInteractWithPost(post);
        postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onClickDisLikePost(Post post, int Position) {
        List<String> strings=post.getDisLikes();
        strings.add(repository.getUser().getUid());
        post.setDisLikes(strings);

        List<String> strings2=post.getDisLikes();
        for(int i=0; i<strings2.size(); i++){
            if(strings2.get(i).equals(repository.getUser().getUid())) {
                strings2.remove(i);
                return;
            }
        }
        post.setLikes(strings2);
        repository.setInteractWithPost(post);
        postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onCancelDisLikePost(Post post, int Position) {
        List<String> strings2=post.getDisLikes();
        for(int i=0; i<strings2.size(); i++){
            if(strings2.get(i).equals(repository.getUser().getUid())) {
                strings2.remove(i);
                return;
            }
        }
        post.setDisLikes(strings2);
        repository.setInteractWithPost(post);
        postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onClickStarPost(Post post, int Position) {
        List<String> strings=post.getStars();
        strings.add(repository.getUser().getUid());
        post.setStars(strings);
        post.setStars(strings);
        repository.setInteractWithPost(post);
        postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onCancelStarPost(Post post, int Position) {
        List<String> strings2=post.getStars();
        for(int i=0; i<strings2.size(); i++){
            if(strings2.get(i).equals(repository.getUser().getUid())) {
                strings2.remove(i);
                return;
            }
        }
        post.setStars(strings2);
        repository.setInteractWithPost(post);
        postAdapter.notifyItemChanged(Position);
    }
}