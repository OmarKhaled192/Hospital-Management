package com.yom.hospitalmanagementyom.database;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.ArrayList;
import java.util.List;

public class MyHomeFirebase {
    @SuppressLint("StaticFieldLeak")
    private static MyHomeFirebase myDatabase;
    private final Context context;
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseStorage firebaseStorage;
    private Post post;
    private List<Post>posts;
    private Hospital hospital;
    private List<Hospital> hospitals;

    private MyHomeFirebase(Context context) {
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public static MyHomeFirebase newInstance(Context context) {
        if (myDatabase == null) {
            myDatabase = new MyHomeFirebase(context);
        }
        return myDatabase;
    }

    public List<Post> getPosts(PostsListener postsListener){
        post=new Post();
        posts=new ArrayList<>();
        firebaseFirestore.collection(Constants.POSTS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    post = document.toObject(Post.class);
                    posts.add(post);
                }
                postsListener.finishGetPosts();
            }
        });
        return posts;
    }

    public List<Hospital> getHospitals(){
//        Hospital hospital1=new Hospital();
//        // Add a new document with a generated ID
//        hospital1.setName("Ain Shams Hospitals");
//        hospital1.setProfile("data:image/png;base64,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");
//        hospital1.setEmail("AinShams@gmail.com");
//        hospital1.setLocation("ElAbbasya");
//        hospital1.setPhone("01142747343");
//        hospital1.setManagerName("Yousef");
//        hospital1.setState("Cairo");
//        firebaseFirestore.collection(Constants.HOSPITALS)
//                .add(hospital1)
//                .addOnSuccessListener(documentReference -> Toast.makeText( context,"DocumentSnapshot added with ID: " + documentReference.getId(),Toast.LENGTH_LONG).show());
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
