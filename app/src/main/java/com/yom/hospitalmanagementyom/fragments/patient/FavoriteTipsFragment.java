package com.yom.hospitalmanagementyom.fragments.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.yom.hospitalmanagementyom.adapter.PostAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentFavoriteTipsBinding;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.List;

public class FavoriteTipsFragment extends Fragment implements PostsListener {

    private FragmentFavoriteTipsBinding binding;
    private Repository repository;
    private List<Post> posts;
    private List<Doctor> doctors;
    private PostAdapter postAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository=new Repository(requireContext());
        posts = repository.getPostsStarted(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteTipsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postAdapter = new PostAdapter(requireContext(), posts, doctors, this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( requireContext() );
        binding.recyclerviewFavoriteTips.setLayoutManager( linearLayoutManager );
        binding.recyclerviewFavoriteTips.setAdapter(postAdapter);

    }


    @Override
    public void finishGetPosts() {
        doctors = repository.getDoctors(posts,this);
    }

    @Override
    public void finishGetDoctors() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}