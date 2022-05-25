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
    private PostAdapter postAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository=new Repository(requireContext());
        //posts = repository.getPostsStarted(this);
        posts = repository.getPosts(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteTipsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postAdapter = new PostAdapter(requireContext(), posts, this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( requireContext() );
        binding.recyclerviewFavoriteTips.setLayoutManager( linearLayoutManager );
        binding.recyclerviewFavoriteTips.setAdapter(postAdapter);
    }


    @Override
    public void finishGetPosts() {

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
        //postAdapter.notifyItemChanged(Position);
    }

    @Override
    public void onCancelLikePost(Post post, int Position) {
        List<String> strings2=post.getDisLikes();
        for(int i=0; i<strings2.size(); i++){
            if(strings2.get(i).equals(repository.getUser().getUid())) {
                strings2.remove(i);
                return;
            }
        }
        post.setDisLikes(strings2);
        repository.setInteractWithPost(post);
    }

    @Override
    public void onClickDisLikePost(Post post, int Position) {
        List<String> strings=post.getDisLikes();
        strings.add(repository.getUser().getUid());
        post.setDisLikes(strings);

        List<String> strings2=post.getLikes();

        for(int i=0; i<strings2.size(); i++){
            if(strings2.get(i).equals(repository.getUser().getUid())) {
                strings2.remove(i);
                return;
            }
        }
        post.setLikes(strings2);
        repository.setInteractWithPost(post);
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
    }

    @Override
    public void onClickStarPost(Post post, int Position) {
        List<String> strings=post.getStars();
        strings.add(repository.getUser().getUid());
        post.setStars(strings);
        post.setStars(strings);
        repository.setInteractWithPost(post);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}