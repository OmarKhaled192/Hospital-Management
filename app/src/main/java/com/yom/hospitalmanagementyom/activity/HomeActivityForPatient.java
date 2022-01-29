package com.yom.hospitalmanagementyom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.PostAdapter;
import com.yom.hospitalmanagementyom.java.Post;

import java.util.ArrayList;
import java.util.List;

//Yousef Shaaban
public class HomeActivityForPatient extends AppCompatActivity {

    DrawerLayout drawer;
    Post post;
    List<Post> posts;
    PostAdapter postAdapter;
    RecyclerView recyclerViewHomePatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_for_patient );
        drawer = findViewById( R.id.drawerLayout );
        post=new Post();
        posts=new ArrayList<>();
        post.setPost( "fjjjf" );
        posts.add( post );
        posts.add( post );
        posts.add( post );
        posts.add( post );
        posts.add( post );
        postAdapter=new PostAdapter( this, posts );
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( this );
        recyclerViewHomePatient=findViewById( R.id.recyclerViewHomePatient );
        recyclerViewHomePatient.setLayoutManager( linearLayoutManager );
        recyclerViewHomePatient.setAdapter( postAdapter );
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