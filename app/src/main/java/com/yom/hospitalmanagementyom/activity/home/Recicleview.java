package com.yom.hospitalmanagementyom.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.Recicladapter;
import com.yom.hospitalmanagementyom.model.Recicl;

import java.util.ArrayList;

public class Recicleview extends AppCompatActivity {
private RecyclerView recicleview;
private Recicladapter recicladapter;
private ArrayList<Recicl> recicls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recicleview);
        recicleview= findViewById(R.id.rc);
        recicls=new ArrayList<>();
    }
}