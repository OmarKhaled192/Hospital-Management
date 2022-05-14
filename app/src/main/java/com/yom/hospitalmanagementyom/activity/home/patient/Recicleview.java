package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.DiseaseAdapter;
import com.yom.hospitalmanagementyom.model.Disease;

import java.util.ArrayList;

public class Recicleview extends AppCompatActivity {
private RecyclerView recicleview;
private DiseaseAdapter recicladapter;
private ArrayList<Disease> recicls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recicleview);
        recicleview= findViewById(R.id.rc);
        recicls=new ArrayList<>();
    }
}