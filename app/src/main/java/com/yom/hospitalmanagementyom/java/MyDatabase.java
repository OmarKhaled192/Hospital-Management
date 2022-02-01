package com.yom.hospitalmanagementyom.java;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class MyDatabase {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference patientsDatabaseReference = firebaseDatabase.getReference("Patients");

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    Context context;
    public MyDatabase(Context context){
        this.context=context;
    }

    public void savePatient(){
        Patient patient=new Patient();
        patient.setProfile("01142747343/01142747343.jpg");
        patient.setName("Yousef");
        patient.setPhone("01142747343");
        patient.setPassword("42747343");
        patient.setUserName("Yousef01142747343");
        patient.setEarthPhone("02747343");
        patient.setEmail("yousefelge141@gmail.com");
       // patient.setDate("25/1/2000");
        //patientsDatabaseReference.child(patientsDatabaseReference.push().getKey()).setValue(patient);


        // Add a new document with a generated ID
        firebaseFirestore.collection("Patients")
                .add(patient)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context,"Yes",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"No",Toast.LENGTH_LONG).show();
                    }
                });
    }

    public Patient CheckPatientIsExist(String userName,String Password){
        Patient patient=new Patient();
        patientsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    //patient
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return patient;
    }
}
