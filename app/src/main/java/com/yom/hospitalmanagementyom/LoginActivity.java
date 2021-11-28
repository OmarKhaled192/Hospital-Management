package com.yom.hospitalmanagementyom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    TextView choose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        choose=findViewById( R.id.choose );
    }

    public void showPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener( (PopupMenu.OnMenuItemClickListener) this );
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hospital:
            case R.id.doctor:
            case R.id.admin:
            case R.id.patient:
                choose.setText( item.getTitle() );
                return true;
            default:
                return false;
        }
    }

    public void login(View view) {
    }

    public void forgetPassword(View view) {
    }

    public void registration(View view) {
    }
}