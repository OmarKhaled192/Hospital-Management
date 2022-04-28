package com.yom.hospitalmanagementyom.database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import com.google.firebase.auth.FirebaseUser;
import com.yom.hospitalmanagementyom.functions.MySharedPreference;
import com.yom.hospitalmanagementyom.listeners.LoginListener;
import com.yom.hospitalmanagementyom.listeners.PhoneVerificationListener;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.listeners.ReadMessage;
import com.yom.hospitalmanagementyom.listeners.SaveDataListener;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Patient;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.List;

public class Repository {
    private final MySharedPreference mySharedPreference;
    private final MyRegistrationFirebase myRegistrationFirebase;
    private final MyHomeFirebase myHomeFirebase;

    public Repository(Context context){
        mySharedPreference=new MySharedPreference(context);
        myRegistrationFirebase = new MyRegistrationFirebase(context);
        myHomeFirebase = new MyHomeFirebase(context);
    }

    //MyRegistrationFirebase
    public void loginIn(String email, String password, LoginListener loginListener){
        myRegistrationFirebase.loginIn(email, password, loginListener);
    }

    public void resetPassword(String email){
        myRegistrationFirebase.resetPassword(email);
    }

    public FirebaseUser getUser(){
        return myRegistrationFirebase.getUser();
    }

    public void savePatient(Patient patient, SaveDataListener saveDataListener) {
        myRegistrationFirebase.savePatient(patient, saveDataListener);
    }


    public void saveHospital(Hospital hospital, SaveDataListener saveDataListener) {
        myRegistrationFirebase.saveHospital(hospital, saveDataListener);
    }

    public void startPhoneNumberVerification(String phoneNumber, Activity activity, ReadMessage readMessage) {
        myRegistrationFirebase.startPhoneNumberVerification(phoneNumber, activity, readMessage);
    }

    public void signInUserByPhone(String total, PhoneVerificationListener phoneVerificationListener) {
        myRegistrationFirebase.signInUserByPhone(total,phoneVerificationListener);
    }

    public void deleteUserPhone(){
        myRegistrationFirebase.deleteUserPhone();
    }

    public void createEmail(String email,String password){
        myRegistrationFirebase.createEmail(email, password);
    }

    public boolean isVerify(){
        return myRegistrationFirebase.isVerify();
    }



    //MySharedPreference
    public void saveString(String key,String value) {
        mySharedPreference.saveString(key,value);
    }

    public String returnStringSharedPreference(String key,String defValue) {
        return mySharedPreference.returnString(key, defValue);
    }


    //MyHomeFirebase
    public void signOut(ProgressDialog progressDialog){
        myHomeFirebase.signOut(progressDialog);
    }
    public List<Post> getPosts(PostsListener postsListener){
        return myHomeFirebase.getPosts(postsListener);
    }
    public List<Hospital> getHospitals() {
        return myHomeFirebase.getHospitals();
    }


}
