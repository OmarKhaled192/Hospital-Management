package com.yom.hospitalmanagementyom.database;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.ArrayList;
import java.util.List;

public class MyHomeFirebase {
    private static MyHomeFirebase myDatabase;
    private Context context;
    private final String POST_KEY="Posts";
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReferencePost;
    private Post post;
    private List<Post>posts;

    private MyHomeFirebase(Context context) {
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReferencePost = firebaseFirestore.collection(POST_KEY);
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
