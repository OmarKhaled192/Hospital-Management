package com.yom.hospitalmanagementyom.activity.home.patient.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.HospitalViewAdapter;
import com.yom.hospitalmanagementyom.adapter.PostAdapter;
import com.yom.hospitalmanagementyom.databinding.FragmentHomePatientBinding;
import com.yom.hospitalmanagementyom.model.Hospital;
import com.yom.hospitalmanagementyom.model.Post;

import java.util.ArrayList;
import java.util.List;

public class HomePatientFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomePatientBinding binding;
    private Hospital hospital;
    private List<Hospital> hospitals;
    private HospitalViewAdapter hospitalViewAdapter;
    private Post post;
    private List<Post> posts;
    private PostAdapter postAdapter;
    private RecyclerView recyclerViewHomePatient, recyclerViewHospitalView;
    private FirebaseFirestore firebaseFirestore;
    private String POSTS="Posts";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout shimmerFrameLayout;
    private FirebaseAuth firebaseAuth;
    private Intent intent;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomePatientBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore=FirebaseFirestore.getInstance();

        hospital=new Hospital();
        hospital.setName("App Name ");
        hospital.setProfile("link");
        hospitals=new ArrayList<>();
        hospitals.add(hospital);
        hospitals.add(hospital);
        hospitals.add(hospital);
        hospitals.add(hospital);
        hospitals.add(hospital);
        hospitalViewAdapter=new HospitalViewAdapter(getContext(), hospitals );
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewHospitalView=binding.recyclerViewHospitalView ;
        recyclerViewHospitalView.setLayoutManager(linearLayoutManager2);
        recyclerViewHospitalView.setAdapter(hospitalViewAdapter);

        post=new Post();
        posts=new ArrayList<>();
        postAdapter=new PostAdapter( getContext(), posts );
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( getContext() );
        recyclerViewHomePatient=binding.recyclerViewHomePatient ;
        recyclerViewHomePatient.setLayoutManager( linearLayoutManager );
        recyclerViewHomePatient.setAdapter( postAdapter );
        getPosts();
        shimmerFrameLayout=binding.shimmer;
        swipeRefreshLayout=binding.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                recyclerViewHospitalView.setVisibility(View.GONE);
                binding.viewHome.setVisibility(View.GONE);
                recyclerViewHomePatient.setVisibility(View.GONE);
                shimmerFrameLayout.showShimmer(true);
                shimmerFrameLayout.startShimmer();
                getPosts();
            }
        } );
        firebaseAuth=FirebaseAuth.getInstance();
    }

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
                binding.viewHome.setVisibility(View.VISIBLE);
                recyclerViewHospitalView.setVisibility(View.VISIBLE);
                postAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing( false );
                swipeRefreshLayout.setColorSchemeColors( getResources().getColor(R.color.red) );
            }
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}