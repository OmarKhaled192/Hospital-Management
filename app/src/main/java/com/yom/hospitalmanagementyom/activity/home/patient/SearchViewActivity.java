package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.yom.hospitalmanagementyom.R;

import com.yom.hospitalmanagementyom.adapter.DrugsAdapter;
import com.yom.hospitalmanagementyom.model.Drug;

import java.util.ArrayList;
import java.util.List;

public class SearchViewActivity extends AppCompatActivity {
private RecyclerView recyclerviewAdmin;
private DrugsAdapter drugsAdapter;
private List<Drug> drugs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search);
        recyclerviewAdmin= findViewById(R.id.recyclerviewAdmin);
        drugs=new ArrayList<>();
        //Intent intent=getIntent();
        //recicls=(ArrayList<Drug>)intent.getSerializableExtra(Constants.DRUGS);
        Drug drug=new Drug();
        drug.setNameDrug("omer");
        for (int i=0;i<5;i++)
            drugs.add(drug);

        drugsAdapter=new DrugsAdapter(this, drugs);
        recyclerviewAdmin.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewAdmin.setAdapter(drugsAdapter);
    }
}