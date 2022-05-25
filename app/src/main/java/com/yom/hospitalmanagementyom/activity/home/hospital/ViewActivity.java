package com.yom.hospitalmanagementyom.activity.home.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.patient.PaymentActivity;
import com.yom.hospitalmanagementyom.adapter.DoctorAdapter;
import com.yom.hospitalmanagementyom.databinding.ActivityViewBinding;
import com.yom.hospitalmanagementyom.model.Admin;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Drug;
import com.yom.hospitalmanagementyom.model.Patient;
import com.yom.hospitalmanagementyom.model.Time;

public class ViewActivity extends AppCompatActivity {

    private ActivityViewBinding binding;
    private Doctor doctor;
    private Admin admin;
    private Drug drug;
    private Time time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        doctor = new Doctor();
        admin = new Admin();
        drug = new Drug();
        time = new Time();

        Intent intent=getIntent();
        String activity = intent.getExtras().getString(Constants.ACTIVITY);
        switch (activity) {
            case Constants.DOCTOR:
                doctor = (Doctor) intent.getSerializableExtra(Constants.OBJECTS);
                setDoctor(doctor);
                break;
            case Constants.ADMIN:
                admin = (Admin) intent.getSerializableExtra(Constants.OBJECTS);
                setAdmin(admin);
                break;
            case Constants.DRUGS:
                drug = (Drug) intent.getSerializableExtra(Constants.OBJECTS);
                setDrug(drug);
                break;
            case Constants.TIME:
                time = (Time) intent.getSerializableExtra(Constants.OBJECTS);
                setTime(time);
                break;
        }

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
            }
        });
    }

    private void setDoctor(Doctor doctor){
        binding.myNameProfileActivity.setText(doctor.getName());
        binding.myPhoneProfileActivity.setText(doctor.getPhone());
        binding.myEmailProfileActivity.setText(doctor.getEmail());
        binding.mySpecialization.setText(doctor.getSpecialization());
    }

    private void setAdmin(Admin admin){
        binding.myNameProfileActivity.setText(admin.getName());
        binding.myPhoneProfileActivity.setText(admin.getPhone());
        binding.myEmailProfileActivity.setText(admin.getEmail());
        binding.mySpecialization.setText(admin.getSpecialization());
    }

    private void setDrug(Drug drug){
        binding.myNameProfileActivity.setText(drug.getNameDrug());
       // binding.myPhoneProfileActivity.setText(drug.g());
        binding.myEmailProfileActivity.setText(doctor.getEmail());
        binding.mySpecialization.setText(doctor.getSpecialization());
    }

    private void setTime(Time time){
        FirebaseFirestore.getInstance().collection(Constants.PATIENT).document(time.getIdPatient()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    Patient patient = task.getResult().toObject(Patient.class);
                    binding.myNameProfileActivity.setText(patient.getName());
                    binding.myPhoneProfileActivity.setText(patient.getPhone());
                    binding.myEmailProfileActivity.setText(time.getTime());
                    binding.mySpecialization.setText(time.getStatus());
                }
            }
        });
    }
}