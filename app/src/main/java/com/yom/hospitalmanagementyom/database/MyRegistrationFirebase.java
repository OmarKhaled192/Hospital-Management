package com.yom.hospitalmanagementyom.database;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.listeners.LoginListener;
import com.yom.hospitalmanagementyom.listeners.ReadMessage;
import com.yom.hospitalmanagementyom.listeners.SaveDataListener;
import com.yom.hospitalmanagementyom.listeners.PhoneVerificationListener;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Patient;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class MyRegistrationFirebase {
    private final Context context;
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseStorage firebaseStorage;
    private final FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private ReadMessage readMessage;

    public MyRegistrationFirebase(Context context) {
        this.context = context;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
            }

            @Override
            public void onCodeSent(@NonNull String  verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
                readMessage.setNumbers();
            }
        };
    }

    public FirebaseUser getUser(){
        return firebaseAuth.getCurrentUser();
    }



    public void savePatient(Patient patient, SaveDataListener saveDataListener) {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.loading);
        TextView nowLoading = dialog.findViewById(R.id.nowLoading);
        ProgressBar progressBarLoading = dialog.findViewById(R.id.progressBarLoading);
        dialog.show();
        patient.setId(getUser().getUid());

        firebaseStorage.getReference(Constants.PATIENTS).child(patient.getId())
                .child(patient.getId() + ".png")
                .putFile(Uri.parse(patient.getProfile()))
                .addOnSuccessListener(taskSnapshot -> {
                    patient.setProfile(Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString());
                    savePatientFirestore(dialog, patient, saveDataListener, taskSnapshot.getUploadSessionUri());
                }).addOnFailureListener(e ->
                    saveDataListener.failSavePatient()
                ).addOnProgressListener(snapshot -> {
                    double progress = (float)(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    String progress1 = progress+"%";
                    nowLoading.setText( progress1 );
                    progressBarLoading.setProgress((int)progress);
                });
    }

    private void savePatientFirestore(Dialog dialog, Patient patient, SaveDataListener saveDataListener, Uri uri){
        firebaseFirestore.collection(Constants.PATIENTS).add(patient)
                .addOnSuccessListener(
                        documentReference -> {
                            saveDataListener.successSavePatient();
                            updateUser(dialog, patient.getName(), uri);
                        })
                .addOnFailureListener(e -> {
                    deleteImageLevel(Constants.PATIENTS,patient.getId());
                    saveDataListener.failSavePatient();
                    dialog.dismiss();
                });
    }

    public void saveHospital(Hospital hospital, SaveDataListener saveDataListener) {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.loading);
        TextView nowLoading = dialog.findViewById(R.id.nowLoading);
        ProgressBar progressBarLoading = dialog.findViewById(R.id.progressBarLoading);
        dialog.show();
        hospital.setId(getUser().getUid());

        firebaseStorage.getReference(Constants.HOSPITALS).child(hospital.getId()).child(hospital.getId() + ".png")
                .putFile(Uri.parse(hospital.getProfile()))
                .addOnSuccessListener(taskSnapshot -> {
                    hospital.setProfile(Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString());
                    saveHospitalFirestore(dialog, hospital, saveDataListener, taskSnapshot.getUploadSessionUri());
                }).addOnFailureListener(e -> saveDataListener.failSaveHospital()
            ).addOnProgressListener(snapshot -> {
            double progress = (float)(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
            String progress1 = progress+"%";
            nowLoading.setText( progress1 );
            progressBarLoading.setProgress((int)progress);
        });
    }

    private void saveHospitalFirestore(Dialog dialog, Hospital hospital, SaveDataListener saveDataListener, Uri uri){
        firebaseFirestore.collection(Constants.HOSPITALS).add(hospital)
                .addOnSuccessListener(
                        documentReference -> {
                            saveDataListener.successSaveHospital();
                            updateUser(dialog, hospital.getName(), uri);
                        })
                .addOnFailureListener(e -> {
                    deleteImageLevel(Constants.HOSPITALS,hospital.getId());
                    saveDataListener.failSaveHospital();
                    dialog.dismiss();
                });
    }

    private void updateUser(Dialog dialog, String name, Uri uri){
        UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(uri).build();
        getUser().updateProfile(userProfileChangeRequest)
                .addOnSuccessListener(unused -> dialog.dismiss())
                .addOnFailureListener(e -> dialog.dismiss());
    }

    private void deleteImageLevel(String child, String child2){
        firebaseStorage.getReference(child).child(child2).delete();
    }

    public void createEmail(String email,String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        sendLink();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                TastyToast.makeText(context,context.getString(R.string.emailErrorVerification),TastyToast.LENGTH_LONG,TastyToast.ERROR);
            }
        });
    }

    private void sendLink(){
        getUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                TastyToast.makeText(context, context.getString(R.string.checkEmail), TastyToast.LENGTH_LONG,TastyToast.ERROR);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                TastyToast.makeText(context, context.getString(R.string.emailErrorVerification), TastyToast.LENGTH_LONG,TastyToast.ERROR);
            }
        });
    }

    public boolean isVerify(){
        return getUser().isEmailVerified();
    }

    public void startPhoneNumberVerification(String phoneNumber, Activity activity, ReadMessage readMessage) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber("+2"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        this.readMessage=readMessage;
    }

    public void signInUser(String total, PhoneVerificationListener phoneVerificationListener) {
        if (mVerificationId.isEmpty())
            return;
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, total);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    phoneVerificationListener.successVerify();
                } else {
                    phoneVerificationListener.failVerify();
                }
            }
        });
    }

    public void signInUser(String email, String password, LoginListener loginListener){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                loginListener.nextToHome();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                TastyToast.makeText(context,context.getString(R.string.failSignIn),TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            }
        });
    }

    public void resetPassword(String email){
        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                TastyToast.makeText(context,context.getString(R.string.checkEmail),TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                TastyToast.makeText(context,context.getString(R.string.failReset),TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
            }
        });
    }



    //Delete FirebaseStorage
    private boolean check ;
    private boolean deleteImageLevel1(String child){
        check = false;
        firebaseStorage.getReference(child).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                check = true;
            }
        });
        return check;
    }
    private boolean deleteImageLevel3(String child, String child2, String child3){
        check = false;
        firebaseStorage.getReference(child).child(child2).child(child3).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                check = true;
            }
        });
        return check;
    }
}