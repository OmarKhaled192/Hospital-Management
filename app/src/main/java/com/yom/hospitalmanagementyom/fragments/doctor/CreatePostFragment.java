package com.yom.hospitalmanagementyom.fragments.doctor;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.doctor.HomeDoctorActivity;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.FragmentCreatePostBinding;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Post;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CreatePostFragment extends Fragment {

    private FragmentCreatePostBinding binding;
    private boolean isCheck, isTime;
    private Uri image, video;
    private ActivityResultLauncher<String> activityResultLauncher, activityResultLauncher2;
    private Post post=new Post();


    public CreatePostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCheck = true;
        isTime = true;
        image = null;
        video = null;

        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if(result!=null) {
                            binding.photoPost.setVisibility(View.VISIBLE);
                            binding.photoPost.setImageURI(result);
                            image = result;
                            binding.publish.setEnabled(true);
                        }
                    }
                }
        );
        activityResultLauncher2=registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if(result)
                            activityResultLauncher.launch("image/*");
                        else
                            Toast.makeText(getContext(),"NO", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getTime();
        binding.edit.setOnClickListener(v -> {
            if(isCheck)
                show();
            else
                hide();
        });

        Repository repository=new Repository(requireContext());

        List<String> strings=new ArrayList<>();
        strings.add(repository.getUser().getUid());
        post= new Post();
        post.setId("");
        post.setPost("");
        post.setImage("");
        post.setStars(strings);
        post.setLikes(strings);
        post.setDisLikes(strings);
        post.setTime("");
        post.setNameDoctor(repository.getUser().getDisplayName());
        post.setProfileDoctor("htt");
        post.setIdDoctor(repository.getUser().getUid());
        post.setVideo("");

        binding.caption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.publish.setEnabled(image != null || video != null || charSequence.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.publish.setOnClickListener(view1 -> {
            post.setTime(binding.timePostForDoctor.getText().toString());
            if(TextUtils.isEmpty(binding.caption.getText().toString()))
                post.setPost("");
            else
                post.setPost(binding.caption.getText().toString());

            if(image == null)
                post.setImage("");
            else
                post.setImage(image.toString());

            if(video == null)
                post.setVideo("");
            else
                post.setImage(video.toString());

            publishPostUri();
        });

        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityResultLauncher2.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        });
    }

    private void getTime(){
        final Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if(!isTime)
                    return;
               updateTime();
            }
        };
        handler.postDelayed(runnable,1000);
    }

    private void updateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("KK:mm:ss a", Locale.ENGLISH);
        Calendar calendar=Calendar.getInstance();
        binding.timePostForDoctor.setText(simpleDateFormat.format(calendar.getTime()));
        getTime();
    }


    private void show() {
        isCheck=false;

        binding.camera.setVisibility(View.VISIBLE);
        binding.gallery.setVisibility(View.VISIBLE);
        binding.video.setVisibility(View.VISIBLE);

        //animation
        binding.edit.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_open));
        binding.camera.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom));
        binding.gallery.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom));
        binding.video.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom));

    }

    private void hide() {
        isCheck=true;
        binding.camera.setVisibility(View.GONE);
        binding.gallery.setVisibility(View.GONE);
        binding.video.setVisibility(View.GONE);

        //animation
        binding.edit.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_close));
        binding.camera.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom));
        binding.gallery.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom));
        binding.video.setAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom));
    }


    public void publishPostUri(){

        post.setId( FirebaseDatabase.getInstance().getReference(Constants.POSTS).push().getKey() );

        if(image!=null){
            pushImage();
        } else if(video !=null){
            pushVideo();
        }else {
            pushText();
        }


    }

    void pushImage() {
        AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
        View view=getLayoutInflater().inflate(R.layout.loading,null);
        TextView nowLoading = view.findViewById(R.id.nowLoading);
        TextView totalLoading = view.findViewById(R.id.totalLoading);
        ProgressBar progressBarLoading = view.findViewById(R.id.progressBarLoading);
        builder.setView(view);
        builder.show();

        FirebaseStorage.getInstance().getReference(Constants.POSTS).child(post.getId()).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(requireContext(),"Yes",Toast.LENGTH_LONG).show();
                post.setImage(taskSnapshot.getUploadSessionUri().getPath());
                pushText();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(),"Yes",Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (float)(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                String progress1 = progress+"%";
                nowLoading.setText( progress1 );
                progressBarLoading.setProgress((int)progress);
            }
        });
    }
    void pushVideo(){
        AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
        View view=getLayoutInflater().inflate(R.layout.loading,null);
        TextView nowLoading = view.findViewById(R.id.nowLoading);
        TextView totalLoading = view.findViewById(R.id.totalLoading);
        ProgressBar progressBarLoading = view.findViewById(R.id.progressBarLoading);
        builder.setView(view);
        builder.show();

        FirebaseStorage.getInstance().getReference(Constants.POSTS).child(post.getId()).putFile(video).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(requireContext(),"Yes",Toast.LENGTH_LONG).show();
                post.setVideo(taskSnapshot.toString());
                pushText();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(),"Yes",Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (float)(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                String progress1 = progress+"%";
                nowLoading.setText( progress1 );
                progressBarLoading.setProgress((int)progress);
            }
        });
    }

    void pushText(){
        AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
        View view=getLayoutInflater().inflate(R.layout.loading,null);
        TextView nowLoading = view.findViewById(R.id.nowLoading);
        TextView totalLoading = view.findViewById(R.id.totalLoading);
        ProgressBar progressBarLoading = view.findViewById(R.id.progressBarLoading);
        builder.setView(view);
        builder.show();
        FirebaseDatabase.getInstance().getReference(Constants.POSTS).child(post.getId()).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBarLoading.setProgress(100);
                progressBarLoading.setVisibility(View.GONE);
                startActivity(new Intent(requireActivity(), HomeDoctorActivity.class));
            }
        });
    }
}