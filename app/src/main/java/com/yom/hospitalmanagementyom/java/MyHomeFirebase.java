package com.yom.hospitalmanagementyom.java;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;

public class MyHomeFirebase {
    private static MyHomeFirebase myDatabase;
    private Context context;
    private final String PATIENTS_KEY = "Patients";
    private final String HOSPITAL_KEY="Hospital";
    private final String POST_KEY="Hospital";
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private CollectionReference collectionReferencePatient, collectionReferenceHospital,collectionReferencePost;
    private StorageReference storageReferencePatient;
    private StorageReference storageReferenceHospital;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private CommonFunction commonFunction;
    private Post post;
    private List<Post>posts;

    private MyHomeFirebase(Context context) {
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        collectionReferencePatient = firebaseFirestore.collection(PATIENTS_KEY);
        storageReferencePatient = firebaseStorage.getReference(PATIENTS_KEY);
        collectionReferenceHospital = firebaseFirestore.collection(HOSPITAL_KEY);
        collectionReferencePost = firebaseFirestore.collection(POST_KEY);
        storageReferenceHospital = firebaseStorage.getReference(HOSPITAL_KEY);
        firebaseAuth=FirebaseAuth.getInstance();
        commonFunction=CommonFunction.getInstance(context);
        post=new Post();
        posts=new ArrayList<>();
    }

    public static MyHomeFirebase newInstance(Context context) {
        if (myDatabase == null) {
            myDatabase = new MyHomeFirebase(context);
        }
        return myDatabase;
    }

    List<Post> getPosts(){
        posts.clear();
        collectionReferencePost.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        post = document.toObject(Post.class);
                        posts.add(post);
                    }
                }
            }
        });
        return posts;
    }
}
