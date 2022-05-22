package com.yom.hospitalmanagementyom.database;

import android.app.Activity;
import android.app.Dialog;
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
import com.google.firebase.auth.UserProfileChangeRequest;
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
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.listeners.ChatListener;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.listeners.SaveDataListener;
import com.yom.hospitalmanagementyom.listeners.SearchListener;
import com.yom.hospitalmanagementyom.model.Admin;
import com.yom.hospitalmanagementyom.model.Chat;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Disease;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Drug;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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



    public ArrayList<Doctor> getAllDoctor() {
        ArrayList<Doctor> doctors=new ArrayList<>();
        firebaseFirestore.collection(Constants.DOCTORS)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                    Doctor doctor =    document.toObject(Doctor.class);
                        doctors.add(doctor);
                    }
                }
            }
        });

               return doctors;
    }

    public ArrayList<Drug> getAllDrugs() {
        ArrayList<Drug> drugs=new ArrayList<>();
        firebaseFirestore.collection(Constants.DRUGS)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Drug drug= document.toObject(Drug.class);
                        drugs.add(drug);
                    }
                }
            }
        });

        return drugs;
    }

    public ArrayList<Admin> getAllAdmins() {
        ArrayList<Admin> admins=new ArrayList<>();
        firebaseFirestore.collection(Constants.DRUGS)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Admin admin= document.toObject(Admin.class);
                        admins.add(admin);
                    }
                }
            }
        });

        return admins;
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
        FirebaseFirestore.getInstance().collection(Constants.DISEASES).whereEqualTo(Constants.NAME,Name)
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
    public void addDoctor(Doctor doctor ,SaveDataListener saveDataListener) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.loading);
        dialog.show();

        firebaseAuth.createUserWithEmailAndPassword(doctor.getEmail(), doctor.getPassword())
                .addOnSuccessListener(authResult ->
                        addDoctorFireStorage(doctor, saveDataListener, dialog))
                .addOnFailureListener(e ->
                        TastyToast.makeText(context, context.getString(R.string.emailErrorVerification), TastyToast.LENGTH_LONG, TastyToast.ERROR));
    }

    private void addDoctorFireStorage(Doctor doctor, SaveDataListener saveDataListener,Dialog dialog) {
        TextView nowLoading = dialog.findViewById(R.id.nowLoading);
        ProgressBar progressBarLoading = dialog.findViewById(R.id.progressBarLoading);
        doctor.setId(getUser().getUid());
        firebaseStorage.getReference(Constants.DOCTORS).child(doctor.getId())
                .child(doctor.getId() + ".png")
                .putFile(Uri.parse(doctor.getProfile()))
                .addOnSuccessListener(taskSnapshot -> {
                    doctor.setProfile(Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString());
                    saveDoctorFirestore(dialog, doctor, saveDataListener, taskSnapshot.getUploadSessionUri());
                }).addOnFailureListener(e ->
                saveDataListener.failSavePatient()
        ).addOnProgressListener(snapshot -> {
            double progress = (float)(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
            String progress1 = progress+"%";
            nowLoading.setText( progress1 );
            progressBarLoading.setProgress((int)progress);
        });
    }
    private void deleteImageLevel(String child, String child2){
        firebaseStorage.getReference(child).child(child2).delete();
    }

    private void saveDoctorFirestore(Dialog dialog, Doctor doctor, SaveDataListener saveDataListener, Uri uri){
        firebaseFirestore.collection(Constants.DOCTORS).add(doctor)
                .addOnSuccessListener(
                        documentReference -> {
                            saveDataListener.successSavePatient();
                            updateUser(dialog, doctor.getName(), uri);
                        })
                .addOnFailureListener(e -> {
                    deleteImageLevel(Constants.DOCTORS,doctor.getId());
                    saveDataListener.failSavePatient();
                    dialog.dismiss();
                });
    }

    private void updateUser(Dialog dialog,String name, Uri uri){
        UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(uri).build();
        getUser().updateProfile(userProfileChangeRequest)
                .addOnSuccessListener(unused -> dialog.dismiss())
                .addOnFailureListener(e -> dialog.dismiss());
    }

    public void updateFirestore(String root, String id, String key,String value){
        firebaseFirestore.collection(root).document(id).update(key, value);
    }
    public void deleteFirestore(String root, String id){
        firebaseFirestore.collection(root).document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }



    public void addAdmin(Admin admin, SaveDataListener saveDataListener) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.loading);
        dialog.show();

        firebaseAuth.createUserWithEmailAndPassword(admin.getEmail(), admin.getPassword())
                .addOnSuccessListener(authResult ->
                        addAdminFireStorage(admin, saveDataListener, dialog))
                .addOnFailureListener(e ->
                        TastyToast.makeText(context, context.getString(R.string.emailErrorVerification), TastyToast.LENGTH_LONG, TastyToast.ERROR));
    }

    private void addAdminFireStorage(Admin admin, SaveDataListener saveDataListener, Dialog dialog) {
        TextView nowLoading = dialog.findViewById(R.id.nowLoading);
        ProgressBar progressBarLoading = dialog.findViewById(R.id.progressBarLoading);
        admin.setId(getUser().getUid());
        firebaseStorage.getReference(Constants.ADMINS).child(admin.getId())
                .child(admin.getId() + ".png")
                .putFile(Uri.parse(admin.getProfile()))
                .addOnSuccessListener(taskSnapshot -> {
                    admin.setProfile(Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString());
                    saveAdminFirestore(dialog, admin, saveDataListener, taskSnapshot.getUploadSessionUri());
                }).addOnFailureListener(e ->
                saveDataListener.failSavePatient()
        ).addOnProgressListener(snapshot -> {
            double progress = (float)(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
            String progress1 = progress+"%";
            nowLoading.setText( progress1 );
            progressBarLoading.setProgress((int)progress);
        });
    }
    private void deleteImageLeveladmin(String child, String child2){
        firebaseStorage.getReference(child).child(child2).delete();
    }

    private void saveAdminFirestore(Dialog dialog, Admin admin, SaveDataListener saveDataListener, Uri uri){
        firebaseFirestore.collection(Constants.ADMINS).add(admin)
                .addOnSuccessListener(
                        documentReference -> {
                            saveDataListener.successSavePatient();
                            updateUser(dialog, admin.getName(), uri);
                        })
                .addOnFailureListener(e -> {
                    deleteImageLevel(Constants.ADMINS, admin.getId());
                    saveDataListener.failSavePatient();
                    dialog.dismiss();
                });
    }

    private void updateadmin(Dialog dialog,String name, Uri uri){
        UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(uri).build();
        getUser().updateProfile(userProfileChangeRequest)
                .addOnSuccessListener(unused -> dialog.dismiss())
                .addOnFailureListener(e -> dialog.dismiss());
    }








    private void addDrugFireStorage(Drug drug, SaveDataListener saveDataListener) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.loading);
        dialog.show();
        TextView nowLoading = dialog.findViewById(R.id.nowLoading);
        ProgressBar progressBarLoading = dialog.findViewById(R.id.progressBarLoading);
        drug.setId(getUser().getUid()); //id mo5talef
        firebaseStorage.getReference(Constants.DRUGS).child(drug.getId())
                .child(drug.getId() + ".png")
                .putFile(Uri.parse(drug.getProfile()))
                .addOnSuccessListener(taskSnapshot -> {
                    drug.setProfile(Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString());
                    saveDrugFirestore(dialog, drug, saveDataListener, taskSnapshot.getUploadSessionUri());
                }).addOnFailureListener(e ->
                saveDataListener.failSavePatient()
        ).addOnProgressListener(snapshot -> {
            double progress = (float)(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
            String progress1 = progress+"%";
            nowLoading.setText( progress1 );
            progressBarLoading.setProgress((int)progress);
        });
    }
    private void deleteImageLeveldrug(String child, String child2){
        firebaseStorage.getReference(child).child(child2).delete();
    }

    private void saveDrugFirestore(Dialog dialog, Drug drug, SaveDataListener saveDataListener, Uri uri){
        firebaseFirestore.collection(Constants.DRUGS).add(drug)
                .addOnSuccessListener(
                        documentReference -> {
                            saveDataListener.successSavePatient();
                            updateUser(dialog, drug.getNameDrug(), uri);
                        })
                .addOnFailureListener(e -> {
                    deleteImageLevel(Constants.DRUGS, drug.getId());
                    saveDataListener.failSavePatient();
                    dialog.dismiss();
                });
    }

    private void updatedrug(Dialog dialog,String name, Uri uri){
        UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(uri).build();
        getUser().updateProfile(userProfileChangeRequest)
                .addOnSuccessListener(unused -> dialog.dismiss())
                .addOnFailureListener(e -> dialog.dismiss());
    }


    public void getdoctor(String Name, SearchListener searchListener) {
        List<Doctor> doctor=new ArrayList<>();
        FirebaseFirestore.getInstance().collection(Constants.DOCTORS).whereEqualTo(Constants.NAME,Name)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Doctor doctor = document.toObject(Doctor.class);
                        doctors.add(doctor);
                    }
                 //   searchListener.finishGetDrugs(doctors);
                }
            }
        });
    }

    //public List<Doctor>


    private String name;
    public String getTextFromBarcode(Context context,Uri uri) {
        name="";
        try{
            InputImage inputImage=InputImage.fromFilePath(context,uri);
            BarcodeScanning.getClient().process(inputImage).addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                @Override
                public void onSuccess(List<Barcode> barcodes) {
                    for(Barcode barcode:barcodes)
                    {
                        int valueType=barcode.getValueType();
                        switch (valueType){
                            case Barcode.TYPE_WIFI:
                                String ssid= Objects.requireNonNull(barcode.getWifi()).getSsid();
                                String password=barcode.getWifi().getPassword();
                                int type=barcode.getWifi().getEncryptionType();
                                break;
                            case Barcode.TYPE_URL:
                                name = Objects.requireNonNull(barcode.getUrl()).getTitle();
                                String url=barcode.getUrl().getUrl();
                                break;
                            default:
                                String data=barcode.getDisplayValue();
                                break;
                        }
                    }
                }
            });

        }catch (Exception ignored){
        }
        return name;
    }

    public String getTextFromImage(Context context,Uri uri) {
        name="";
        try{
            InputImage inputImage=InputImage.fromFilePath(context,uri);
            TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS).process(inputImage)
                    .addOnSuccessListener(text -> name = text.getText());
        }catch (Exception ignored){

        }
        return name;
    }
}
