package com.yom.hospitalmanagementyom.database;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.google.firebase.auth.FirebaseUser;
import com.yom.hospitalmanagementyom.functions.MySharedPreference;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Post;

import java.util.List;

public class Repository {
    private Context context;
    private final MySharedPreference mySharedPreference;
    private final MyRegistrationFirebase myRegistrationFirebase;
    private final MyHomeFirebase myHomeFirebase;

    public Repository(Context context){
        this.context=context;
        mySharedPreference=MySharedPreference.newInstance(context);
        myRegistrationFirebase=MyRegistrationFirebase.getInstance(context);
        myHomeFirebase = MyHomeFirebase.newInstance(context);
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


    //Home Patient
    public List<Post> getPosts(PostsListener postsListener){
        return myHomeFirebase.getPosts(postsListener);
    }
    public List<Hospital> getHospitals() {
        return myHomeFirebase.getHospitals();
    }


}
