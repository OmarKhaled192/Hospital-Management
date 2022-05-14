package com.yom.hospitalmanagementyom.database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yom.hospitalmanagementyom.functions.CommonFunction;
import com.yom.hospitalmanagementyom.functions.MySharedPreference;
import com.yom.hospitalmanagementyom.listeners.ChatListener;
import com.yom.hospitalmanagementyom.listeners.LoginListener;
import com.yom.hospitalmanagementyom.listeners.PhoneVerificationListener;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.listeners.ReadMessage;
import com.yom.hospitalmanagementyom.listeners.SaveDataListener;
import com.yom.hospitalmanagementyom.listeners.SearchListener;
import com.yom.hospitalmanagementyom.model.Chat;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Drug;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Patient;
import com.yom.hospitalmanagementyom.model.Post;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final MySharedPreference mySharedPreference;
    private final MyRegistrationFirebase myRegistrationFirebase;
    private final MyHomeFirebase myHomeFirebase;
    private final CommonFunction commonFunction;

    public Repository(Context context){
        mySharedPreference=new MySharedPreference(context);
        myRegistrationFirebase = new MyRegistrationFirebase(context);
        myHomeFirebase = new MyHomeFirebase(context);
        commonFunction = new CommonFunction();
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

    public List<Hospital> getHospitals() {
        return myHomeFirebase.getHospitals();
    }

    public List<Post> getPosts(PostsListener postsListener){
        return myHomeFirebase.getPosts(postsListener);
    }

    public List<Doctor> getDoctorPosts(List<Post> posts, PostsListener postsListener){
        return myHomeFirebase.getDoctorPosts(posts, postsListener);
    }

    public void setInteractWithPost(String postId, String nameField, String userId) {
        myHomeFirebase.setInteractWithPost(postId, nameField, userId);
    }

    public void deleteInteractWithPost(String postId, String deleteField, String userId) {
        myHomeFirebase.deleteInteractWithPost(postId, deleteField, userId);
    }

    public List<Post> getPostsStarted(PostsListener postsListener){
        return myHomeFirebase.getPostsStarted(postsListener);
    }

    public List<Chat> getLastMessage(ChatListener chatListener){
       return myHomeFirebase.getLastMessage(chatListener);
    }

    public List<Doctor> getDoctorChats(List<Chat> chats){
        return myHomeFirebase.getDoctorChats(chats);
    }

    public void getDrugs(String Name, SearchListener searchListener) {
        myHomeFirebase.getDrugs(Name, searchListener);
    }

    //CommonFunction
    public boolean checkExistId(List<String> list, String id){
        return commonFunction.checkExistId(list, id);
    }




}
