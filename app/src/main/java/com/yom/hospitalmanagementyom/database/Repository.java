package com.yom.hospitalmanagementyom.database;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.functions.CommonFunction;
import com.yom.hospitalmanagementyom.functions.MySharedPreference;
import com.yom.hospitalmanagementyom.listeners.ChatListener;
import com.yom.hospitalmanagementyom.listeners.LoginListener;
import com.yom.hospitalmanagementyom.listeners.PhoneVerificationListener;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.listeners.ReadMessage;
import com.yom.hospitalmanagementyom.listeners.SaveDataListener;
import com.yom.hospitalmanagementyom.listeners.SearchListener;
import com.yom.hospitalmanagementyom.model.Admin;
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

    public void saveDrugFirestore(Dialog dialog, Drug drug, SaveDataListener saveDataListener, Uri uri){
     myHomeFirebase.saveDrugFirestore(dialog, drug, saveDataListener, uri);

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

    public void setReceiver(Context context,int hour,int minute){
       commonFunction.setReceiver(context,hour,minute);
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

    public List<Post> getPosts(PostsListener postsListener) {
        return myHomeFirebase.getPosts(postsListener);
    }

    public List<Doctor> getDoctorPosts(List<Post> posts, PostsListener postsListener){
        return myHomeFirebase.getDoctorPosts(posts, postsListener);
    }

    public void setInteractWithPost(Post post) {
        myHomeFirebase.setInteractWithPost(post);
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

    public String getTimeNow() {
        return commonFunction.getTimeNow();
    }


    public void getDisease(String Name, SearchListener searchListener) {
        myHomeFirebase.getDisease(Name, searchListener);
    }

    public List<Chat> getMessages(String id){
        return myHomeFirebase.getMessages(id);
    }

    public void setWriteNow(String Status, String id, String idChat){
       myHomeFirebase.setWriteNow(Status, id, idChat);
    }

    public void SendMessage(Chat chat){
        myHomeFirebase.SendMessage(chat);
    }

    public void setLastSeenByChatId(String id, String status) {
        myHomeFirebase.setLastSeenByChatId(id, status);
    }

    public void setStatus(String Root,String status){
        myHomeFirebase.setStatus(Root, status);
    }

    public void addDoctor(Doctor doctor ,SaveDataListener saveDataListener) {
        myHomeFirebase.addDoctor(doctor, saveDataListener);
    }

    public void addAdmin(Admin admin, SaveDataListener saveDataListener) {
        myHomeFirebase.addAdmin(admin, saveDataListener);
    }

    public String getTextFromImage(Context context, Uri uri) {
        return myHomeFirebase.getTextFromImage(context, uri);
    }

    public String getTextFromBarcode(Context context,Uri uri) {
        return myHomeFirebase.getTextFromBarcode(context, uri);
    }

    public void saveInt(String key,int value) {
        mySharedPreference.saveInt(key, value);
    }

    public int returnInt(String key,int defValue) {
        return mySharedPreference.returnInt(key, defValue);
    }
    public void callPhone(Context context,String ph) {
        commonFunction.callPhone(context, ph);
    }


    public List<Admin> getAllAdmin(String idHospital) {
        return myHomeFirebase.getAllAdmin(idHospital);
    }

    public List<Doctor> getAllDoctors(String idHospital) {
        return myHomeFirebase.getAllDoctors(idHospital);
    }


    public List<Drug> getAllDrugs(String idHospital) {
        return myHomeFirebase.getAllDrugs(idHospital);
    }

    public List<Doctor> getAllDoctors(LoginListener loginListener) {
        return myHomeFirebase.getAllDoctors(loginListener);
    }
}
