package com.yom.hospitalmanagementyom.activity.home.patient.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.model.Constants;

public class HealthCare extends AppCompatActivity {

    private Button book_appointment,Chat_bot_service;
    private TextView t,des;
    private ImageView im;
    private Repository repository;
    private TextInputEditText friendPhone;
    private View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care);
        friendPhone = findViewById(R.id.friendPhone);
        Chat_bot_service = findViewById(R.id.Chat_bot_service);
        v = findViewById(R.id.Description_health);
        t = v.findViewById(R.id.Head_id);
        des = v.findViewById(R.id.Description_id);
        im = v.findViewById(R.id.imageView_id);
        t.setText(getResources().getStringArray(R.array.Heads)[2]);
        im.setImageResource(R.drawable.health_care);
        des.setText(getResources().getStringArray(R.array.Descriptions)[2]);

       // repository = new Repository(getApplicationContext());
       // repository.setReceiver(this);
    }

    public void chat_bot(View v)
    {

        if(friendPhone.getText().toString().length()==0)
            TastyToast.makeText(this,"No number entered",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
        else
            {
                Repository rep = new Repository(this);
                rep.saveString(Constants.PHONE,friendPhone.getText().toString());

                Intent intent=new Intent(getBaseContext(), ByChatting.class);
                startActivity( intent );
            }
    }
}