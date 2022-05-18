package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.Parcode;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivitySearchPatientBinding;
import com.yom.hospitalmanagementyom.listeners.SearchListener;
import com.yom.hospitalmanagementyom.model.Disease;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Drug;
import com.yom.hospitalmanagementyom.model.Hospital;

import java.io.Serializable;
import java.util.List;

public class SearchPatientActivity extends AppCompatActivity implements SearchListener {
    EditText e1;
    Button b1,b2,b3,b4;
    String s;
    private static final String tag="my tag";
    InputImage inputImage;
    TextRecognizer textRecognizer;
    String t;
    private ActivitySearchPatientBinding binding;
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textRecognizer= TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        e1=findViewById(R.id.editText8);
        b1=findViewById(R.id.sename);
        b2=findViewById(R.id.sedies);
        b3=findViewById(R.id.sephoto);
        b4=findViewById(R.id.separ);
        s=e1.toString();
        repository = new Repository(this);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b3.getId()==view.getId()){

                    Intent camera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(camera.resolveActivity(getPackageManager())!=null){
                        startActivityForResult(camera,10);

                    }

                }

            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10&resultCode==RESULT_OK){
            Uri imageuri=data.getData();
            convertimagetext(imageuri);
        }
    }

    private void convertimagetext(Uri imageuri) {
        try{
            inputImage=InputImage.fromFilePath(getApplicationContext(),imageuri);
            Task<Text> result=textRecognizer.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {
                            t=text.getText();
                            repository.getDrugs(s, SearchPatientActivity.this);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(tag," error"+e.getMessage());
                        }
                    });
        }catch (Exception e){
            Log.d(tag,"convert image to text: error"+e.getMessage());
        }

    }







    public void searchbyname(View view) {
        if (s.equals("")) {
            TastyToast.makeText(getApplicationContext(),"Please Enter The Word",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        } else {
            repository.getDrugs(s, this);
        }
    }






    public void searchdiese(View view) {
        if (s.equals("")) {
            TastyToast.makeText(getApplicationContext(),"Please Enter The Word",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        } else {
            repository.getDisease(s, this);
        }
    }





    @Override
    public void finishGetDrugs(List<Drug> drugs) {
        Intent intent= new Intent(getApplicationContext(), Recicleview.class);
        intent.putExtra("Drugs", (Serializable) drugs);
        startActivity(intent);
    }

    public void searchbyparcode(View view) {
if(R.id.separ==view.getId()){
    Intent intent=new Intent(this, Parcode.class);
    startActivity(intent);
}
    }
}