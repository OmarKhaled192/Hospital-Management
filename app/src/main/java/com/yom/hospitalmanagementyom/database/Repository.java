package com.yom.hospitalmanagementyom.database;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.yom.hospitalmanagementyom.functions.MySharedPreference;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Post;
import com.yom.hospitalmanagementyom.listeners.PostDao;

import java.util.List;

public class Repository {
    private Context context;
    private static Repository repository;
    private final MySharedPreference mySharedPreference;
    private final MyRegistrationFirebase myRegistrationFirebase;
    private final PostDao postDao;
    private final LiveData<List<Post>> posts;
    private final MyHomeFirebase myHomeFirebase;

    private Repository(Application application){
        this.context=application.getBaseContext();
        mySharedPreference=MySharedPreference.newInstance(context);
        myRegistrationFirebase=MyRegistrationFirebase.getInstance(context);
        PostRoom postRoom = PostRoom.getInstance(application);
        postDao = postRoom.postDao();
        posts = postDao.getAllPostFromRoom();
        myHomeFirebase = MyHomeFirebase.newInstance(context);
    }

    public static Repository newInstance(Application application){
        if(repository==null){
            repository=new Repository(application);
        }
        return repository;
    }

    public void
    saveStringSharedPreference(String key,String value) {
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
        PostRoom.databaseWriteExecutor.execute(() -> postDao.insert(post));
    }


    //Home Patient
    public List<Post> getPosts(PostsListener postsListener){
        return myHomeFirebase.getPosts(postsListener);
    }
    public List<Hospital> getHospitals() {
        return myHomeFirebase.getHospitals();
    }


}
