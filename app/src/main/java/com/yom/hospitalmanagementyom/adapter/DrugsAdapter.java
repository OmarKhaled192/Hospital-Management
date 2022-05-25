package com.yom.hospitalmanagementyom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.hospital.ViewActivity;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Drug;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DrugsAdapter extends RecyclerView.Adapter<DrugsAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameDoctorForHospital, specializationDoctorForHospital, workerDoctorForHospital;
        CircleImageView profileDoctorForHospital;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDoctorForHospital=itemView.findViewById(R.id.nameDoctorForHospital);
            specializationDoctorForHospital=itemView.findViewById(R.id.specializationDoctorForHospital);
            workerDoctorForHospital=itemView.findViewById(R.id.workerDoctorForHospital);
            profileDoctorForHospital=itemView.findViewById(R.id.profileDoctorForHospital);
        }
    }
    private Context context;
    private List<Drug> drugs;
    public DrugsAdapter(Context context, List<Drug> drugs){
        this.context=context;
        this.drugs=drugs;
    }

    @NonNull
    public DrugsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_manger,parent,false);
        return new DrugsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drug drug= drugs.get(position);
        holder.nameDoctorForHospital.setText(drug.getNameDrug());
        holder.specializationDoctorForHospital.setText(drug.getProfile());
        holder.workerDoctorForHospital.setText(drug.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ViewActivity.class);
                intent.putExtra(Constants.ACTIVITY,Constants.DRUGS);
                intent.putExtra(Constants.OBJECTS,drug);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }
}
