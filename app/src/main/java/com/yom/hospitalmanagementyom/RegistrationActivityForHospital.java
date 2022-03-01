package com.yom.hospitalmanagementyom.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.databinding.ActivityRegistrationForHospitalBinding;
import com.yom.hospitalmanagementyom.java.Hospital;

//Omar Khaled
public class RegistrationActivityForHospital extends AppCompatActivity{

	private ActivityRegistrationForHospitalBinding binding;
	private String profile,choose;
	private final String HOSPITAL_KEY="Hospital";
	private final String ACTIVITY_KEY="Activity";
	private ActivityResultLauncher<String> activityResultLauncher;
	private boolean check=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		binding= ActivityRegistrationForHospitalBinding.inflate(getLayoutInflater());
		setContentView( binding.getRoot() );
		binding.toolbarRegistrationHospital.setNavigationIcon(R.drawable.back);
		binding.toolbarRegistrationHospital.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		binding.Location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				choose= (String) parent.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				choose= (String) parent.getItemAtPosition(0);
			}
		});

		activityResultLauncher=registerForActivityResult(
				new ActivityResultContracts.GetContent(),
				new ActivityResultCallback<Uri>() {
					@Override
					public void onActivityResult(Uri result) {
						if(result!=null) {
							profile = result.getPath();
							binding.Profile.setImageURI(result);
							check = true;
						}
					}
				}
		);
	}

	public void next(View view) {
		String HospitalName=binding.HospitalName.getText().toString();
		String ManagerName=binding.ManagerName.getText().toString();
		if(!check)
			TastyToast.makeText(getBaseContext(), getString(R.string.profile), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
		else if (HospitalName.length() == 0)
			binding.box1.setError( getString(R.string.hospital_name) );
		else if (ManagerName.length() == 0)
			binding.box1.setError( getString(R.string.Manager_Name) );
		else {
			Hospital hospital=new Hospital();
			hospital.setProfile(profile);
			hospital.setLocation(choose);
			hospital.setName(HospitalName);
			hospital.setManagerName(ManagerName);
			hospital.setState(HOSPITAL_KEY);
			Intent intent = new Intent(RegistrationActivityForHospital.this, CommonRegistrationActivity.class);
			intent.putExtra(HOSPITAL_KEY, hospital);
			intent.putExtra(ACTIVITY_KEY,HOSPITAL_KEY);
			startActivity(intent);
		}
	}

	public void pick(View view) {
		activityResultLauncher.launch("image/*");
	}
}