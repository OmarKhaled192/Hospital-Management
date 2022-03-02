package com.yom.hospitalmanagementyom.database;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.functions.CommonFunction;
import com.yom.hospitalmanagementyom.listeners.LoginListener;
import com.yom.hospitalmanagementyom.listeners.SaveDataListener;
import com.yom.hospitalmanagementyom.listeners.PhoneVerificationListener;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Patient;

import java.util.concurrent.TimeUnit;


public class MyRegistrationFirebase {
    private static MyRegistrationFirebase myDatabase;
    private Context context;
    private final String PATIENTS_KEY = "Patients";
    private final String HOSPITAL_KEY="Hospital";
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private CollectionReference collectionReferencePatient;
    private StorageReference storageReferencePatient;
    private CollectionReference collectionReferenceHospital;
    private StorageReference storageReferenceHospital;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private CommonFunction commonFunction;

    private MyRegistrationFirebase(Context context) {
        this.context = context;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReferencePatient = firebaseFirestore.collection(PATIENTS_KEY);
        collectionReferenceHospital = firebaseFirestore.collection(HOSPITAL_KEY);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReferencePatient = firebaseStorage.getReference(PATIENTS_KEY);
        storageReferenceHospital = firebaseStorage.getReference(HOSPITAL_KEY);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
            }

            @Override
            public void onCodeSent(@NonNull String  verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        commonFunction=CommonFunction.newInstance();
    }

    public static MyRegistrationFirebase getInstance(Context context) {
        if (myDatabase == null) {
            myDatabase = new MyRegistrationFirebase(context);
        }
        return myDatabase;
    }


    public FirebaseUser getUser(){
        return firebaseUser;
    }



    public void savePatient(Patient patient, SaveDataListener emailVerificationListener) {
        storageReferencePatient.child(patient.getName()).child(commonFunction.getTimeNow() + ".png")
                .putFile(Uri.parse(patient.getProfile())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                patient.setProfile(PATIENTS_KEY + "/" + patient.getName() + "/" + commonFunction.getTimeNow() + ".png");
                collectionReferencePatient.add(patient).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Yes", Toast.LENGTH_LONG).show();
                        emailVerificationListener.successSavePatient();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "No", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void saveHospital(Hospital hospital, SaveDataListener emailVerificationListener) {
        storageReferenceHospital.child(hospital.getName()).child(commonFunction.getTimeNow() + ".png")
                .putFile(Uri.parse(hospital.getProfile())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                hospital.setProfile(PATIENTS_KEY + "/" + hospital.getName() + "/" + commonFunction.getTimeNow() + ".png");
                collectionReferenceHospital.add(hospital).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Yes", Toast.LENGTH_LONG).show();
                        emailVerificationListener.successSaveHospital();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "No", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
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
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
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
        return firebaseUser.isEmailVerified();
    }

    public void startPhoneNumberVerification(String phoneNumber, Activity activity) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber("+2"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
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

    public void SignInUser(String email, String password, LoginListener loginListener){
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
}