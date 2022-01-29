package com.yom.hospitalmanagementyom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yom.hospitalmanagementyom.R;

//Omar Khaled
public class RegistrationActivityForHospital extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    TextView ch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration_for_hospital );
        ch = findViewById(R.id.ch_location);

    }

    public void showCitiesMenu(View v) {
        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.cities_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {


            switch (item.getItemId()) {
                case R.id.Alex:
                case R.id.Aswan:
                case R.id.Asyut:
                case R.id.Beheira:
                case R.id.Beni_Suef:
                case R.id.Cairo:
                case R.id.Dakahlia:
                case R.id.Damietta:
                case R.id.Faiyum:
                case R.id.Gharbia:
                case R.id.Giza:
                case R.id.Ismailia:
                case R.id.Kafr_El_Sheikh:
                case R.id.Luxor:
                case R.id.Matruh:
                case R.id.Minya:
                case R.id.Monufia:
                case R.id.New_Valley:
                case R.id.North_Sinai:
                case R.id.Port_Said:
                case R.id.Qalyubia:
                case R.id.Qena:
                case R.id.Red_Sea:
                case R.id.Sharqia:
                case R.id.Sohag:
                case R.id.South_Sinai:
                case R.id.Suez:
                    ch.setText( item.getTitle() );
                    return true;
                default:
                    return false;
            }
    }
    public void returnToChooce(View v)
    {
        startActivity( new Intent(this,ChooseActivity.class)  );

    }
    public void gotoSignIn(View v)
    {
        startActivity( new Intent(this,LoginActivity.class)  );

    }
}