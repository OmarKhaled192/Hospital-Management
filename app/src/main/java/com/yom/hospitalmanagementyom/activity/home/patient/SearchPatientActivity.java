package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivitySearchPatientBinding;
import com.yom.hospitalmanagementyom.listeners.SearchListener;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Drug;
import com.yom.hospitalmanagementyom.model.Hospital;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchPatientActivity extends AppCompatActivity implements SearchListener {
    EditText e1;
    Button b1,b2,b3,b4;
    String s;
    private ActivitySearchPatientBinding binding;
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        e1=findViewById(R.id.editText8);
        b1=findViewById(R.id.sename);
        s=e1.toString();
        repository = new Repository(this);
    }

    public void searchbyname(View view) {
        if (s.equals("")) {
            TastyToast.makeText(getApplicationContext(),"Please Enter The Word",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        } else {
            repository.getDrugs(s, this);
        }
    }


    public void searchdiese(View view) {
        if (s.equals("")) {
            TastyToast.makeText(getApplicationContext(),"Please Enter The Word",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        } else {
            Query query = FirebaseDatabase.getInstance().getReference("Dieses").orderByChild("Name").equalTo(s);
        }
    }

    @Override
    public void finishDetDrugs(List<Drug> drugs) {
        Intent intent= new Intent(getApplicationContext(), Recicleview.class);
        intent.putExtra("Drugs", (Serializable) drugs);
        startActivity(intent);
    }
}