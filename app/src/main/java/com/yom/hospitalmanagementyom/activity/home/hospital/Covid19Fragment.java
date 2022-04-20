package com.yom.hospitalmanagementyom.activity.home.hospital;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.ml.Covid;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Covid19Fragment extends Fragment {

    private ImageView covidImage;
    private Button detect;
    private TextView covidResult;
    private ActivityResultLauncher<String> activityResultLauncher, activityResultLauncher2;
    private Bitmap bitmap;

    public Covid19Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        covidImage.setImageURI(result);
                        try {
                            bitmap= MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(),result);
                        } catch (IOException e) {
                            e.printStackTrace();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_covid19, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        covidImage=view.findViewById(R.id.covidImage);
        detect=view.findViewById(R.id.detect);
        covidResult = view.findViewById(R.id.covidResult);

        covidImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityResultLauncher2.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        });

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bitmap=Bitmap.createScaledBitmap(bitmap,64,64,true);

                try {
                    Covid model = Covid.newInstance(requireContext());
                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 64, 64, 3}, DataType.FLOAT32);
                    TensorImage tensorImage=new TensorImage(DataType.FLOAT32);
                    tensorImage.load(bitmap);
                    ByteBuffer byteBuffer=tensorImage.getBuffer();
                    inputFeature0.loadBuffer(byteBuffer);
                    // Runs model inference and gets result.
                    Covid.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                    // Releases model resources if no longer used.
                    model.close();

                    String s="";
                    if(outputFeature0.getFloatArray()[0]==1)
                        s+=getString(R.string.positive);
                     if(outputFeature0.getFloatArray()[1]==1)
                         s+=getString(R.string.negative);
                     if(outputFeature0.getFloatArray()[2]==1)
                         s+=getString(R.string.viralPhenomena);

                     covidResult.setText(s);

                } catch (IOException e) {
                    // TODO Handle the exception
                }

            }
        });
    }
}