package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.yom.hospitalmanagementyom.R;

import com.yom.hospitalmanagementyom.adapter.DrugsAdapter;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Disease;
import com.yom.hospitalmanagementyom.model.Drug;

import java.util.ArrayList;
import java.util.Collections;

public class Recicleview extends AppCompatActivity {
private RecyclerView recicleview;
private DrugsAdapter recicladapter;
private ArrayList<Drug> drugs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recicleview);
        recicleview= findViewById(R.id.recyclerviewAdmin);
        drugs=new ArrayList<>();
        //Intent intent=getIntent();
        //recicls=(ArrayList<Drug>)intent.getSerializableExtra(Constants.DRUGS);
        Drug drug=new Drug();
        drug.setNameDrug("omer");
        drugs.add(drug);
        drugs.add(drug);
        drugs.add(drug);

        recicladapter=new DrugsAdapter(this, Collections.singletonList(drugs),"D");
        recicleview.setLayoutManager(new LinearLayoutManager(this));
        recicleview.setAdapter(recicladapter);
    }
}