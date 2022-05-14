package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.Recicleview;
import com.yom.hospitalmanagementyom.databinding.ActivitySearchPatientBinding;

public class SearchPatientActivity extends AppCompatActivity {
    EditText e1;
    Button b1,b2,b3,b4;
    String s;
    private ActivitySearchPatientBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        e1=findViewById(R.id.editText8);
        b1=findViewById(R.id.sename);
        s=e1.toString();
    }

    public void searchbyname(View view) {
        if (s.equals("")) {
            TastyToast.makeText(getApplicationContext(),"Please Enter The Word",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        } else {
            Query query = FirebaseDatabase.getInstance().getReference("Drugs").orderByChild("Name").equalTo(s);

        }
    }


    public void searchdiese(View view) {
        if (s.equals("")) {
            TastyToast.makeText(getApplicationContext(),"Please Enter The Word",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        } else {
            Query query = FirebaseDatabase.getInstance().getReference("Dieses").orderByChild("Name").equalTo(s);
        }
    }

}