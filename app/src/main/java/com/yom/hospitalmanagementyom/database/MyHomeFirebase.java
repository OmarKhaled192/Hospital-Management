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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.listeners.ChatListener;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.listeners.SearchListener;
import com.yom.hospitalmanagementyom.model.Chat;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Disease;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Drug;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Post;

import java.util.ArrayList;
import java.util.List;

public class MyHomeFirebase {
    private final Context context;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseDatabase firebaseDatabase;
    private final FirebaseStorage firebaseStorage;
    private Doctor doctor;

    public MyHomeFirebase(Context context) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
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
    public List<Post> getPosts(PostsListener postsListener){
        post=new Post();
        posts=new ArrayList<>();
        firebaseDatabase.getReference(Constants.POSTS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    post = d.getValue(Post.class);
                    posts.add(post);
                }
                postsListener.finishGetPosts();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return posts;
    }


    private List<Doctor> doctors;
    public List<Doctor> getDoctorPosts(List<Post> posts, PostsListener postsListener){
        doctor = new Doctor();
        doctors = new ArrayList<>();
        for (int i=0; i<posts.size();i++) {
            doctor = getDoctor(posts.get(i).getIdDoctor());
            doctors.add(doctor);
            if(i == posts.size()-1)
                postsListener.finishGetDoctors();
        }
        return doctors;
    }

    public void setInteractWithPost(String postId, String nameField, String userId) {
        firebaseDatabase.getReference(Constants.POSTS).child(postId).child(nameField).setValue(userId);
    }

    public void deleteInteractWithPost(String postId, String deleteField, String userId) {
        firebaseDatabase.getReference(Constants.POSTS).child(postId).child(deleteField).child(userId).removeValue();
    }


    public List<Post> getPostsStarted(PostsListener postsListener){
        post=new Post();
        posts=new ArrayList<>();
        firebaseDatabase.getReference(Constants.POSTS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    post = d.getValue(Post.class);
                    for (int i=0; i<post.getLikes().size(); i++) {
                        if (post.getLikes().get(i).equals(getUser().getUid())) {
                            posts.add(post);
                            return;
                        }
                    }
                }
                postsListener.finishGetPosts();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return posts;
    }

    private List<Chat> chats;
    public List<Chat> getLastMessage(ChatListener chatListener){
        chats = new ArrayList<>();
        firebaseDatabase.getReference(Constants.LAST_MESSAGES).child(getUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    firebaseDatabase.getReference(Constants.LAST_MESSAGES).child(getUser().getUid()).child(d.getKey())
                            .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Chat chat = snapshot.getValue(Chat.class);
                            chats.add(chat);
                            assert chat != null;
                            if(!chat.getSeen().equals(Constants.SEEN))
                                setSeenByChatId(d.getKey(), chat.getId(),Constants.NOT_SEEN);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                chatListener.getLastMessageFinish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return chats;
    }

    public List<Doctor> getDoctorChats(List<Chat> chats){
        doctor = new Doctor();
        doctors = new ArrayList<>();
        for (int i=0; i<chats.size();i++) {
            doctor = getDoctor(chats.get(i).getIdReceiver());
            doctors.add(doctor);
        }
        return doctors;
    }

    public List<Chat> getMessages(String id){
        chats = new ArrayList<>();
        firebaseDatabase.getReference(Constants.CHATS).child(getUser().getUid()).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Chat chat = d.getValue(Chat.class);
                    chats.add(chat);
                    assert chat != null;
                    if(!chat.getSeen().equals(Constants.SEEN))
                        setSeenByChatId(id, chat.getId(),Constants.SEEN);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return chats;
    }

    private void setSeenByChatId(String id, String idMessage, String status) {
        firebaseDatabase.getReference(Constants.CHATS).child(getUser().getUid())
                .child(id).child(idMessage).child(Constants.SEEN).setValue(status);

        firebaseDatabase.getReference(Constants.CHATS).child(id).child(idMessage)
                .child(getUser().getUid()).child(Constants.SEEN).setValue(status);
    }

    public void setLastSeenByChatId(String id, String status) {
        firebaseDatabase.getReference(Constants.LAST_MESSAGES).child(getUser().getUid())
                .child(id).child(Constants.SEEN).setValue(status);

        firebaseDatabase.getReference(Constants.LAST_MESSAGES).child(id)
                .child(getUser().getUid()).child(Constants.SEEN).setValue(status);
    }

    private Doctor getDoctor(String id){
        doctor = new Doctor();
        firebaseFirestore.collection(Constants.DOCTORS).whereEqualTo( Constants.ID, id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    doctor = (Doctor) task.getResult().toObjects(Doctor.class);
                }
            }
        });
        return doctor;
    }

    public void setWriteNow(String Status, String id, String idChat){
        firebaseDatabase.getReference(Constants.LAST_MESSAGES)
                .child(id).child(getUser().getUid()).child(Constants.TIME).setValue(Status);

        firebaseDatabase.getReference(Constants.LAST_MESSAGES)
                .child(getUser().getUid()).child(id).child(Constants.TIME).setValue(Status);

        firebaseDatabase.getReference(Constants.CHATS)
                .child(id).child(getUser().getUid()).child(idChat).child(Constants.TIME).setValue(Status);

        firebaseDatabase.getReference(Constants.CHATS)
                .child(getUser().getUid()).child(id).child(idChat).child(Constants.TIME).setValue(Status);
    }

    public void SendMessage(Chat chat){
        firebaseDatabase.getReference(Constants.LAST_MESSAGES)
                .child(chat.getIdSender()).child(chat.getIdReceiver()).setValue(chat);

        firebaseDatabase.getReference(Constants.LAST_MESSAGES)
                .child(chat.getIdReceiver()).child(chat.getIdSender()).setValue(chat);

        firebaseDatabase.getReference(Constants.CHATS)
                .child(chat.getIdSender()).child(chat.getIdReceiver()).child(chat.getId()).setValue(chat);

        firebaseDatabase.getReference(Constants.CHATS)
                .child(chat.getIdReceiver()).child(chat.getIdSender()).child(chat.getId()).setValue(chat);
    }

    public void setStatus(String Root,String status){
        firebaseFirestore.collection(Root).document(getUser().getUid()).update(Constants.STATUS, status);
    }

    public void getDrugs(String Name, SearchListener searchListener) {
        List<Drug> drugs=new ArrayList<>();
        FirebaseFirestore.getInstance().collection(Constants.DRUGS).whereEqualTo(Constants.NAME,Name)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Drug drug = document.toObject(Drug.class);
                        drugs.add(drug);
                    }
                    searchListener.finishGetDrugs(drugs);
                }
            }
        });
    }

    public void getDisease(String Name, SearchListener searchListener) {
        FirebaseFirestore.getInstance().collection(Constants.DISLIKES).whereEqualTo(Constants.NAME,Name)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Disease disease = (Disease) task.getResult().toObjects(Disease.class);
                    List<Drug> drugs=new ArrayList<>();
                    for(String id : disease.getDrugs()){
                        FirebaseFirestore.getInstance().collection(Constants.DRUGS).whereEqualTo(Constants.ID,id)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Drug drug = (Drug) task.getResult().toObjects(Drug.class);
                                    drugs.add(drug);
                                }
                            }
                        });
                    }
                    searchListener.finishGetDrugs(drugs);
                }
            }
        });
    }



//    public void publishPost(Activity activity,Post post, Uri uri){
//        firebaseFirestore.collection(Constants.POSTS).document(post.getTime()).add(post)
//                .addOnSuccessListener(documentReference ->{
//                        post.setId(documentReference.getId());
//                        publishPostUri(activity, post, uri);
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
