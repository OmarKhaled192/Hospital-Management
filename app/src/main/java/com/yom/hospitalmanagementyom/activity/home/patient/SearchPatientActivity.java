package com.yom.hospitalmanagementyom.activity.home.patient;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.mlkit.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.databinding.ActivitySearchPatientBinding;
import com.yom.hospitalmanagementyom.listeners.SearchListener;
import com.yom.hospitalmanagementyom.model.Drug;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class SearchPatientActivity extends AppCompatActivity implements SearchListener {

    private String status;
    TextRecognizer textRecognizer;
    String t;
    private ActivitySearchPatientBinding binding;
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new Repository(this);
        binding.searchByName.setOnClickListener(view -> {
            if(Objects.requireNonNull(binding.searchText.getText()).length()==0)
                binding.searchText.setError(getString(R.string.nameOfDrug));
            else
                searchByName(binding.searchText.getText().toString());
        });

        binding.searchByDisease.setOnClickListener(view -> {
            if(Objects.requireNonNull(binding.searchText.getText()).length()==0)
                binding.searchText.setError(getString(R.string.nameOfDisease));
            else
                searchByDisease(binding.searchText.getText().toString());
        });

        binding.searchByPhoto.setOnClickListener(view -> {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(SearchPatientActivity.this);
            status = "Photo";
        });

        binding.searchByBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SearchPatientActivity.this);
                status = "Barcode";
            }
        });
    }

    private void searchByDisease(String disease) {
        repository.getDisease(disease, this);
    }

    private void searchByName(String name) {
        repository.getDrugs(name, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uri = result.getUri();
                if (status.equals("Photo"))
                    searchByName( repository.getTextFromImage(getApplicationContext(),uri) );
                else  if (status.equals("Barcode"))
                    searchByName( repository.getTextFromBarcode(getApplicationContext(),uri) );
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void finishGetDrugs(List<Drug> drugs) {
        Intent intent= new Intent(getApplicationContext(), SearchViewActivity.class);
        intent.putExtra("Drugs", (Serializable) drugs);
        startActivity(intent);
    }

}