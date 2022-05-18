package com.yom.hospitalmanagementyom;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaSession2Service;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class Parcode extends AppCompatActivity {
    Button b1;
    ImageView image;
    TextView t1;
    BarcodeScanner scanner;
    ActivityResultLauncher<Intent> cameralancher;
    ActivityResultLauncher<Intent> galarylancher;
    InputImage inputImage;
    private  static final String tag="my tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcode);
        Intent intent=getIntent();
        b1 = findViewById(R.id.par);
        image = findViewById(R.id.ivqcode);
        t1 = findViewById(R.id.textresulr);

                scanner = BarcodeScanning.getClient();

        cameralancher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data=result.getData();
                        try{
                            Bitmap photo =(Bitmap)data.getExtras().get("data");
                            inputImage=InputImage.fromBitmap(photo,0);
                            inputImage=InputImage.fromFilePath(Parcode.this,data.getData());
                            processimage();

                        }catch (Exception e){
                            Log.d(tag," error"+e.getMessage());
                        }

                    }
                });


        galarylancher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data=result.getData();
                        try{
                            inputImage=InputImage.fromFilePath(Parcode.this,data.getData());
                            processimage();
                        }catch (Exception e){
                            Log.d(tag," error"+e.getMessage());
                        }

                    }
                });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] option={"camera","gallery"};
                AlertDialog.Builder builder=new AlertDialog.Builder(Parcode.this);
                builder.setTitle("pick a option");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            Intent camerint=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            cameralancher.launch(camerint);

                        }
                        else
                        {
                            Intent storint=new Intent();
                            storint.setType("image/*");
                            storint.setAction(Intent.ACTION_GET_CONTENT);
                            galarylancher.launch(storint);
                        }

                    }
                });
                builder.show();

            }
        });
    }

    private void processimage() {
        image.setVisibility(View.GONE);
        t1.setVisibility(View.VISIBLE);
        Task<List<Barcode>> result=scanner.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        for(Barcode barcode:barcodes)
                        {
                            int valuetype=barcode.getValueType();
                            switch (valuetype){
                                case Barcode.TYPE_WIFI:
                                    String ssid=barcode.getWifi().getSsid();
                                    String password=barcode.getWifi().getPassword();
                                    int type=barcode.getWifi().getEncryptionType();
                                    t1.setText("SSID :" +ssid+"\n"+"Password  :" +password+"\n"+"Type  :" +type+"\n");
                                    break;
                                case Barcode.TYPE_URL:
                                    String title=barcode.getUrl().getTitle();
                                    String url=barcode.getUrl().getUrl();
                                    t1.setText("title :" +title+"\n"+"url  :" +url+"\n");
                                    break;
                                default:
                                    String data=barcode.getDisplayValue();
                                    t1.setText("result :" +data+"\n");
                                    break;

                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(tag," onFailure"+e.getMessage());

                    }
                });
    }


}