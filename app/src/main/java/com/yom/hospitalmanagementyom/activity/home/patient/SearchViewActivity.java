package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.yom.hospitalmanagementyom.R;

import com.yom.hospitalmanagementyom.adapter.DrugsAdapter;
import com.yom.hospitalmanagementyom.model.Constants;
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

        Intent intent=getIntent();
        drugs=new ArrayList<>();
        drugs= (List<Drug>) intent.getSerializableExtra(Constants.DRUGS);

        drugsAdapter=new DrugsAdapter(this, drugs);
        recyclerviewAdmin.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewAdmin.setAdapter(drugsAdapter);
    }
}