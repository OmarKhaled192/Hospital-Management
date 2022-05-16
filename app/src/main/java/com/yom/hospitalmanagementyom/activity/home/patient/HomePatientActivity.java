package com.yom.hospitalmanagementyom.activity.home.patient;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivityHomePatientBinding;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.MyReceiver;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePatientActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomePatientBinding binding;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarHomePatient.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_chat, R.id.nav_favorite_tips)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_home_patient);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View v = binding.navView.getHeaderView(0);
        CircleImageView circleImageView=v.findViewById(R.id.myProfile);
        TextView name = v.findViewById(R.id.name);
        TextView email = v.findViewById(R.id.email);
        circleImageView.setOnClickListener(v1 -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra(Constants.ACTIVITY, Constants.PATIENT);
            startActivity(intent);
        });

        repository = new Repository(getApplicationContext());

        circleImageView.setImageURI(repository.getUser().getPhotoUrl());
        name.setText(repository.getUser().getDisplayName());
        email.setText(repository.getUser().getEmail());

        repository.setStatus(Constants.PATIENT, Constants.ONLINE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.searchPatient)
            startActivity(new Intent(this, SearchPatientActivity.class));
        else if (item.getItemId() == R.id.signOutPatient)
            exitDialog();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_patient);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }


    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle(getString(R.string.signOut))
                .setMessage(getString(R.string.signOutQuestion));
        builder.setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
            dialogInterface.dismiss();
            ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setTitle(getString(R.string.signOut));
            progressDialog.create();
            progressDialog.show();
            repository.signOut(progressDialog);
        }).setNegativeButton(getString(R.string.no), (dialogInterface, i) ->
                dialogInterface.dismiss()).create();

        builder.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        repository.setStatus(Constants.PATIENT, Constants.OFFLINE);
    }
}