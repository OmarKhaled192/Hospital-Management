package com.yom.hospitalmanagementyom.database;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.yom.hospitalmanagementyom.functions.CommonFunction;
import com.yom.hospitalmanagementyom.functions.MySharedPreference;
import com.yom.hospitalmanagementyom.model.Post;

import java.util.List;

public class Repository {
    private Context context;
    private static Repository repository;
    private MySharedPreference mySharedPreference;
    private MyRegistrationFirebase myRegistrationFirebase;
    private PostDao postDao;
    private LiveData<List<Post>> posts;

    private Repository(Application application){
        this.context=context;
        mySharedPreference=MySharedPreference.newInstance(context);
        myRegistrationFirebase=MyRegistrationFirebase.getInstance(context);
        PostRoom postRoom = PostRoom.getInstance(application);
        postDao = postRoom.postDao();
        posts = postDao.getAllPostFromRoom();
    }

    public static Repository newInstance(Application application){
        if(repository==null){
            repository=new Repository(application);
        }
        return repository;
    }

    public void saveStringSharedPreference(String key,String value) {
        mySharedPreference.saveString(key,value);
    }

    public String returnStringSharedPreference(String key,String defValue) {
        return mySharedPreference.returnString(key, defValue);
    }

    public FirebaseUser getUser(){
        return myRegistrationFirebase.getUser();
    }

    public LiveData<List<Post>> getAllPosts() {
        return posts;
    }

    public void insert(Post post) {
        PostRoom.databaseWriteExecutor.execute(() -> {
            postDao.insert(post);
        });
    }

}
