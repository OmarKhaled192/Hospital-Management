package com.yom.hospitalmanagementyom.database;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.UploadTask;
import com.yom.hospitalmanagementyom.functions.MySharedPreference;
import com.yom.hospitalmanagementyom.listeners.LoginListener;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.listeners.SaveDataListener;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Patient;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.List;

public class Repository {
    private final Context context;
    private final MySharedPreference mySharedPreference;
    private final MyRegistrationFirebase myRegistrationFirebase;
    private final MyHomeFirebase myHomeFirebase;

    public Repository(Context context){
        this.context=context;
        mySharedPreference=new MySharedPreference(context);
        myRegistrationFirebase = new MyRegistrationFirebase(context);
        myHomeFirebase = MyHomeFirebase.newInstance(context);
    }

    public void resetPassword(String email){
        myRegistrationFirebase.resetPassword(email);
    }

    public void signInUser(String email, String password, LoginListener loginListener){
        myRegistrationFirebase.signInUser(email,password,loginListener);
    }


    public void saveString(String key,String value) {
        mySharedPreference.saveString(key,value);
    }

    public String returnStringSharedPreference(String key,String defValue) {
        return mySharedPreference.returnString(key, defValue);
    }

    public void savePatient(Patient patient, SaveDataListener saveDataListener) {
        myRegistrationFirebase.savePatient(patient, saveDataListener);
    }

    public void saveHospital(Hospital hospital, SaveDataListener saveDataListener) {
        myRegistrationFirebase.saveHospital(hospital, saveDataListener);
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
