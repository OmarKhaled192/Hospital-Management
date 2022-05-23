package com.yom.hospitalmanagementyom.activity.home.patient.healthcare;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.patient.HomePatientActivity;
import com.yom.hospitalmanagementyom.activity.home.patient.SearchPatientActivity;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.ml.Covid;
import com.yom.hospitalmanagementyom.model.Constants;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Questions extends AppCompatActivity {
    private ImageView covidImage;
    private Button detect;
    private TextView covidResult;
    private ActivityResultLauncher<String> activityResultLauncher3, activityResultLauncher2;
    private Bitmap bitmap;

    private TextView counter , questionText;
    private int i;
    private Repository repository;

    private String count_list[];
    private ActivityResultLauncher<String> activityResultLauncher;

    private RadioButton ans1, ans2;
    private   Button nextBtn;
    private String questions[],firstAnswers[],SecondAnswers[];
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        i = 0;
        repository = new Repository(this);


        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if (result)
                        repository.callPhone(getApplicationContext(), repository.returnStringSharedPreference(Constants.PHONE, ""));
                    else
                        TastyToast.makeText(this, getString(R.string.noPermissionCall), TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                }
        );

        // for covid 19 detection :
        bitmap = null;

        activityResultLauncher2 = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result)
                            activityResultLauncher.launch("image/*");
                        else
                            Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_LONG).show();
                    }
                }
        );

        activityResultLauncher3 = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        covidImage.setImageURI(result);
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );


        count_list = new String[]{"2/4", "3/4", "4/4"};
        questions = new String[]{getString(R.string.Q1), getString(R.string.Q2), getString(R.string.Q3), getString(R.string.Q4)};
        firstAnswers = new String[]{getString(R.string.ans1Q1), getString(R.string.ans1Q2), getString(R.string.ans1Q3), getString(R.string.ans1Q4)};
        SecondAnswers = new String[]{getString(R.string.ans2Q1), getString(R.string.ans2Q2), getString(R.string.ans2Q3), getString(R.string.ans2Q4)};

        counter = findViewById(R.id.count);
        questionText = findViewById(R.id.Q);
        ans1 = findViewById(R.id.btn_ans_1);
        ans2 = findViewById(R.id.btn_ans_2);
        radioGroup = findViewById(R.id.radioGroup);
        nextBtn = findViewById(R.id.nextBtn);
    }

    public void next(View view) {
        if(i==0){
            if(radioGroup.getCheckedRadioButtonId()==R.id.btn_ans_1)
                TastyToast.makeText(this,"Great,continue !!" ,TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
            else if(radioGroup.getCheckedRadioButtonId()==R.id.btn_ans_2)
                TastyToast.makeText(this,"Sad,you should take a warning in the next time",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
            }

        else if(i==1){
            if (radioGroup.getCheckedRadioButtonId()==R.id.btn_ans_1)
                {
                    BottomSheetDialog bottomSheetDialog=new BottomSheetDialog( this,R.style.bottomTheme );
                    View bottom= LayoutInflater.from( this ).inflate( R.layout.bottom_about,findViewById( R.id.containerBottom ) );
                    bottomSheetDialog.setContentView( bottom );

                    ImageButton callFriend=bottom.findViewById( R.id.callFriend );
                    ImageButton callHospital=bottom.findViewById( R.id.callHospital );
                    ImageButton cancel=bottom.findViewById( R.id.cancel );

                    callFriend.setOnClickListener(v -> activityResultLauncher.launch(Manifest.permission.CALL_PHONE) );

                    //==>>>>>>>>>>>>>>>
                        callHospital.setOnClickListener(v -> activityResultLauncher.launch(Manifest.permission.CALL_PHONE) );
                   //==>>>>>>>>>>>>>>>>>


                    cancel.setOnClickListener(v -> bottomSheetDialog.dismiss() );

                    bottomSheetDialog.show();
                }
            else if(radioGroup.getCheckedRadioButtonId()==R.id.btn_ans_2)
                TastyToast.makeText(this,"Great,continue !!" ,TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
        }

        else if(i==2){
            if (radioGroup.getCheckedRadioButtonId()==R.id.btn_ans_1)
            {
                Intent intent=new Intent(getBaseContext(), SearchPatientActivity.class);
                startActivity( intent );
            }
            else if(radioGroup.getCheckedRadioButtonId()==R.id.btn_ans_2)
                TastyToast.makeText(this,"Great,continue !!" ,TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();

            nextBtn.setText("Finish");
        }
        else if(i==3){
            if (radioGroup.getCheckedRadioButtonId()==R.id.btn_ans_1)
            {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog( this,R.style.bottomTheme );
                View bottom= LayoutInflater.from( this ).inflate( R.layout.fragment_covid19,findViewById( R.id.containerBottom ) );
                bottomSheetDialog.setContentView( bottom );

                CovidDetect(bottom);
                bottomSheetDialog.show();
            }
            else if(radioGroup.getCheckedRadioButtonId()==R.id.btn_ans_2)
            {
                TastyToast.makeText(this,"Great,continue !!" ,TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();
                Intent intent=new Intent(getBaseContext(), HomePatientActivity.class);
                startActivity( intent );
            }

        }



        counter.setText(count_list[i]);
        i++;
        questionText.setText(questions[i]);
        ans1.setText(firstAnswers[i]);
        ans2.setText(SecondAnswers[i]);


    }

    private void CovidDetect(View bottom) {
        covidImage = bottom.findViewById(R.id.covidImage);
        detect = bottom.findViewById(R.id.detect);
        covidResult = bottom.findViewById(R.id.covidResult);

        covidImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityResultLauncher2.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        });

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 64, 64, true);


                    try {
                        Covid model = Covid.newInstance(getApplicationContext());
                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 64, 64, 3}, DataType.FLOAT32);
                        TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                        tensorImage.load(bitmap);
                        ByteBuffer byteBuffer = tensorImage.getBuffer();
                        inputFeature0.loadBuffer(byteBuffer);
                        // Runs model inference and gets result.
                        Covid.Outputs outputs = model.process(inputFeature0);
                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                        // Releases model resources if no longer used.
                        model.close();

                        String s = "";
                        if (outputFeature0.getFloatArray()[0] == 1)
                            s += getString(R.string.positive);
                        if (outputFeature0.getFloatArray()[1] == 1)
                            s += getString(R.string.negative);
                        if (outputFeature0.getFloatArray()[2] == 1)
                            s += getString(R.string.viralPhenomena);

                        covidResult.setText(s);

                    } catch (IOException e) {
                        // TODO Handle the exception
                    }
                }
            }
        });
    }
}