package com.yom.hospitalmanagementyom.database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.ArrayList;
import java.util.List;

public class MyHomeFirebase {
    private final Context context;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseStorage firebaseStorage;

    public MyHomeFirebase(Context context) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    private FirebaseUser getUser(){
        return firebaseAuth.getCurrentUser();
    }

    public void signOut(ProgressDialog progressDialog){
        firebaseAuth.signOut();
        progressDialog.dismiss();
    }

    private Hospital hospital;
    private List<Hospital> hospitals;
    public List<Hospital> getHospitals(){
        hospital=new Hospital();
        hospitals=new ArrayList<>();
        firebaseFirestore.collection(Constants.HOSPITALS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    hospital = document.toObject(Hospital.class);
                    hospitals.add(hospital);
                }
            }
        });
        return hospitals;
    }

    private Post post;
    private List<Post>posts;
    public List<Post> getPosts(){
        post=new Post();
        posts=new ArrayList<>();
        firebaseFirestore.collection(Constants.POSTS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    post = document.toObject(Post.class);
                    posts.add(post);
                }
            }
        });
        return posts;
    }

    private Doctor doctor;
    private List<Doctor> doctors;
    public List<Doctor> getDoctors(List<Post> posts, PostsListener postsListener){
        doctor = new Doctor();
        doctors = new ArrayList<>();
        for (int i=0; i<posts.size();i++) {
            firebaseFirestore.collection(Constants.DOCTORS).whereEqualTo( Constants.ID, posts.get(i).getIdDoctor())
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        doctor = (Doctor) task.getResult().toObjects(Doctor.class);
                        doctors.add(doctor);
                    }
                }
            });
            if(i == posts.size()-1)
                postsListener.finishGetPosts();
        }
        return doctors;
    }

    public void setInteractWithPost(String postId, String nameField, String deleteField, String userId){
        firebaseFirestore.collection(Constants.POSTS).document(postId).update(nameField, userId);
        firebaseFirestore.collection(Constants.POSTS).document(postId);
    }



//    public void publishPost(Activity activity,Post post, Uri uri){
//        firebaseFirestore.collection(Constants.POSTS).document(post.getTime()).add(post).
//                .addOnSuccessListener(documentReference ->{
//                        post.setId(documentReference.getId());
//                        publishPostUri(activity, post, uri)
//                        TastyToast.makeText(context, context.getString(R.string.publishSuccess),
//                                TastyToast.LENGTH_LONG,TastyToast.SUCCESS ).show())
//                }
//                .addOnFailureListener(e ->
//                        TastyToast.makeText(context, context.getString(R.string.publishFailure),
//                                TastyToast.LENGTH_LONG,TastyToast.ERROR ).show());
//    }

    public void publishPostUri(Uri uri, String userId, String postId){
        firebaseStorage.getReference(Constants.POSTS).child(userId).child(postId)
                .putFile(uri).addOnProgressListener(snapshot -> {
                   long count = snapshot.getTotalByteCount();
                   long stop = snapshot.getBytesTransferred();
                });
    }


    public void publishPostUri(Activity activity,Post post, Uri uri){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view=activity.getLayoutInflater().inflate(R.layout.loading,null);
        TextView nowLoading = view.findViewById(R.id.nowLoading);
        ProgressBar progressBarLoading = view.findViewById(R.id.progressBarLoading);
        builder.setView(view);
        builder.show();

        firebaseStorage.getReference(Constants.POSTS).child(post.getId()).child(post.getId())
                .putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(activity,"Yes",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity,"No"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (float)(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                String progress1 = progress+"%";
                nowLoading.setText( progress1 );
                progressBarLoading.setProgress((int)progress);
            }
        });
    }

}
