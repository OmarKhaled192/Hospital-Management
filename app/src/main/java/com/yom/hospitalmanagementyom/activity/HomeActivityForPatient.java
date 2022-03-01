package com.yom.hospitalmanagementyom.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.PostAdapter;
import com.yom.hospitalmanagementyom.java.Post;

import java.util.ArrayList;
import java.util.List;


public class HomeActivityForPatient extends AppCompatActivity {

  DrawerLayout drawer;
  Post post;
  List<Post> posts;
  PostAdapter postAdapter;
  RecyclerView recyclerViewHomePatient;
  FirebaseFirestore firebaseFirestore;
  String POSTS="Posts";
  SwipeRefreshLayout swipeRefreshLayout;
  ShimmerFrameLayout shimmerFrameLayout;
  FirebaseAuth firebaseAuth;
  Intent intent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
    setContentView(R.layout.activity_home_for_patient);

    drawer = findViewById( R.id.drawerLayout );
    firebaseFirestore=FirebaseFirestore.getInstance();
    post=new Post();
    posts=new ArrayList<>();
    postAdapter=new PostAdapter( this, posts );
    LinearLayoutManager linearLayoutManager=new LinearLayoutManager( this );
    recyclerViewHomePatient=findViewById( R.id.recyclerViewHomePatient );
    recyclerViewHomePatient.setLayoutManager( linearLayoutManager );
    recyclerViewHomePatient.setAdapter( postAdapter );
    getPosts();
    shimmerFrameLayout=findViewById(R.id.shimmer);
    swipeRefreshLayout=findViewById( R.id.swipeRefreshLayout );
    swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerViewHomePatient.setVisibility(View.GONE);
        shimmerFrameLayout.showShimmer(true);
        shimmerFrameLayout.startShimmer();
        getPosts();
      }
    } );
    firebaseAuth=FirebaseAuth.getInstance();
  }

  public void set(View view) {
    drawer.openDrawer( GravityCompat.START);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate( R.menu.setting_menu_of_patient, menu );
    return true;
  }

  /*
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    Toast.makeText(this, ""+item.getItemId(), Toast.LENGTH_SHORT).show();
    switch (item.getItemId()){
      case R.id.hospital1:
        intent=new Intent(this,HospitalViewingActivityForPatient.class);
        startActivity(intent);
        break;
      case R.id.signOut1:
        firebaseAuth.signOut();
        intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        break;
      default:
        break;

    }
    return super.onOptionsItemSelected(item);
  }

   */

  void getPosts(){
    posts.clear();
    firebaseFirestore.collection(POSTS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
      @Override
      public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
          for (QueryDocumentSnapshot document : task.getResult()) {
            post = document.toObject(Post.class);
            posts.add(post);
          }
        }
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.hideShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerViewHomePatient.setVisibility(View.VISIBLE);
        postAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing( false );
        swipeRefreshLayout.setColorSchemeColors( getResources().getColor(R.color.red) );
      }
    });
  }
}