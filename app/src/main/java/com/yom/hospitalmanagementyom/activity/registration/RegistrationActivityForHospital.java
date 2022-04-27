package com.yom.hospitalmanagementyom.activity.registration;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.sdsmdg.tastytoast.TastyToast;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.databinding.ActivityRegistrationForHospitalBinding;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Hospital;
import java.util.Objects;

//Omar Khaled
public class RegistrationActivityForHospital extends AppCompatActivity{

	private ActivityRegistrationForHospitalBinding binding;
	private String profile = null;
	private int position = -1;
	private ActivityResultLauncher<String> activityResultLauncher, activityResultLauncher2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		binding= ActivityRegistrationForHospitalBinding.inflate(getLayoutInflater());
		setContentView( binding.getRoot() );

		activityResultLauncher=registerForActivityResult(
				new ActivityResultContracts.GetContent(),
				result -> {
					profile=result.getPath();
					binding.pickOfProfile.setImageURI(result);
				}
		);

		activityResultLauncher2=registerForActivityResult(
				new ActivityResultContracts.RequestPermission(),
				result -> {
					if(result)
						activityResultLauncher.launch( "image/*");
					else
						TastyToast.makeText(getApplicationContext(),"NO", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
				}
		);

		ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Locations));
		binding.Location.setAdapter(adapterItems);
		binding.Location.setOnItemClickListener((adapterView, view, i, l) -> position = i);

		binding.pickOfProfile.setOnClickListener(view -> activityResultLauncher2.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE));

		binding.next.setOnClickListener(view -> next());
	}

	private void next() {
		String Location= Objects.requireNonNull(binding.Location.getText()).toString();
		String HospitalName= Objects.requireNonNull(binding.HospitalName.getText()).toString();
		String ManagerName= Objects.requireNonNull(binding.ManagerName.getText()).toString();
		if(profile == null)
			TastyToast.makeText(getBaseContext(), getString(R.string.profile), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
		else if(Location.length() == 0)
			TastyToast.makeText(getBaseContext(), getString(R.string.Location), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
		else if (HospitalName.length() == 0)
			binding.box1.setError( getString(R.string.hospital_name) );
		else if (ManagerName.length() == 0)
			binding.box1.setError( getString(R.string.Manager_Name) );
		else {
			Hospital hospital=new Hospital();
			hospital.setProfile(profile);
			hospital.setLocation(getResources().getStringArray(R.array.Locations)[position]);
			hospital.setName(HospitalName);
			hospital.setManagerName(ManagerName);
			hospital.setType(Constants.HOSPITAL_KEY);
			Intent intent = new Intent(RegistrationActivityForHospital.this, CommonRegistrationActivity.class);
			intent.putExtra(Constants.HOSPITAL_KEY, hospital);
			intent.putExtra(Constants.ACTIVITY_KEY,Constants.HOSPITAL_KEY);
			startActivity(intent);
		}
	}

}