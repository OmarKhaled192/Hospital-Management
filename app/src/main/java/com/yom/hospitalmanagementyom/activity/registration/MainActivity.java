package com.yom.hospitalmanagementyom.activity.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yom.hospitalmanagementyom.activity.home.doctor.HomeDoctorActivity;
import com.yom.hospitalmanagementyom.activity.home.patient.HomePatientActivity;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivityMainBinding;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot() );

        repository=new Repository(getApplicationContext());


        Toast.makeText(getApplicationContext(),"ss",Toast.LENGTH_LONG).show();
        List<String> list=new ArrayList<>();
        list.add(FirebaseDatabase.getInstance().getReference(Constants.POSTS).getKey());
        list.add(FirebaseFirestore.getInstance().collection("Joo").getId());
        list.add(FirebaseDatabase.getInstance().getReference(Constants.POSTS).getKey());
        list.add(FirebaseFirestore.getInstance().collection("Joo").getId());
        list.add(FirebaseDatabase.getInstance().getReference(Constants.POSTS).getKey());

        Post post = new Post();
        post.setId(String.valueOf(FirebaseDatabase.getInstance().getReference(Constants.POSTS).push().getKey()));
        post.setIdDoctor(FirebaseDatabase.getInstance().getReference(Constants.POSTS).getKey());
        post.setPost("Yousef");
        post.setTime("10:00 PM");
        post.setVideo("");
        post.setImage("");
        post.setDisLikes(list);
        post.setLikes(list);
        post.setStars(list);
        FirebaseDatabase.getInstance().getReference(Constants.POSTS).child(post.getId()).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Yes",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"No",Toast.LENGTH_LONG).show();
            }
        });
    }

    void checkUser(){
        Intent intent=new Intent();
        String typeUser=repository.returnStringSharedPreference(Constants.TYPE_USER,"");
        if(repository.getUser()!=null){
            switch (typeUser) {
                case Constants.PATIENT:
                    intent.setClass(this, HomePatientActivity.class);
                    break;
                case Constants.HOSPITAL:
                    intent.setClass(this, VerificationActivity.class);
                    break;
                case Constants.DOCTOR:
                    intent.setClass(this, HomeDoctorActivity.class);
                    break;
                case Constants.ADMIN:
                    intent.setClass(this, RegistrationActivityForHospital.class);
                    break;
                default:
                    intent.setClass(this, SlideActivity.class);
                    break;
            }
        }
        else
            intent.setClass(this, SlideActivity.class);

        startActivity(intent);
        finish();
    }


}