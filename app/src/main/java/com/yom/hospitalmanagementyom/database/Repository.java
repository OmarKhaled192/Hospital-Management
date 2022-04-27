package com.yom.hospitalmanagementyom.database;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.UploadTask;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.functions.MySharedPreference;
import com.yom.hospitalmanagementyom.listeners.LoginListener;
import com.yom.hospitalmanagementyom.listeners.PhoneVerificationListener;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.listeners.ReadMessage;
import com.yom.hospitalmanagementyom.listeners.SaveDataListener;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Patient;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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



    public void saveString(String key,String value) {
        mySharedPreference.saveString(key,value);
    }

    public String returnStringSharedPreference(String key,String defValue) {
        return mySharedPreference.returnString(key, defValue);
    }
    //Home Patient
    public List<Post> getPosts(PostsListener postsListener){
        return myHomeFirebase.getPosts(postsListener);
    }
    public List<Hospital> getHospitals() {
        return myHomeFirebase.getHospitals();
    }


}
