package com.yom.hospitalmanagementyom.activity.home.patient.favorite_tips;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.List;

public class FavoriteTipsViewModel extends AndroidViewModel {

    private final Repository repository;
    private final LiveData<List<Post>> posts;

    public FavoriteTipsViewModel(Application application) {
        super(application);
        repository = Repository.newInstance(application);
        posts = repository.getAllPosts();
    }

    LiveData<List<Post>> getAllPosts() {
        return posts;
    }

    void insert(Post post) {
        repository.insert(post);
    }
}