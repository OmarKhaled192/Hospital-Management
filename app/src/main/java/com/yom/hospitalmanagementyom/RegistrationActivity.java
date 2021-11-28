package com.yom.hospitalmanagementyom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class RegistrationActivity extends AppCompatActivity {
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        b1=findViewById(R.id.b1);
    }
    public void registration(View view) {
        startActivity( new Intent(this,ChooseActivity.class)  );
    }


    public void next(View view) {
        final Intent intent =new Intent(this,Verfication.class);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Do you want change phone ");builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(intent);
            }
        });
        builder.show();

    }
}