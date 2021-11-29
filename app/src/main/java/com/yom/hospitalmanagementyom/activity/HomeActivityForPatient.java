package com.yom.hospitalmanagementyom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.yom.hospitalmanagementyom.R;

public class HomeActivityForPatient extends AppCompatActivity {

    int i;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_for_patient );
        drawer = findViewById( R.id.drawerLayout );
    }

    public void set(View view) {
        drawer.openDrawer( GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.setting_menu_of_patient, menu );
        return true;
    }
}