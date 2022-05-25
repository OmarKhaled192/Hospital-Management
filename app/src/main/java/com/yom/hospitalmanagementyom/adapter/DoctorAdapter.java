package com.yom.hospitalmanagementyom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.activity.home.hospital.ViewActivity;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Drug;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder>{

    private final Context context;
    private final List<Doctor> doctors;
    public DoctorAdapter(Context context, List<Doctor> doctors){
        this.context=context;
        this.doctors=doctors;
    }

    @NonNull
    public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_manger,parent,false);
        return new DoctorAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor doctor= doctors.get(position);
        holder.nameDoctorForHospital.setText(doctor.getName());
        holder.specializationDoctorForHospital.setText(doctor.getSpecialization());
        holder.workerDoctorForHospital.setText(doctor.getWorker());
        Picasso.with(context).load(doctor.getProfile()).error(R.color.teal_700)
                .into(holder.profileDoctorForHospital);
        if(doctor.getWorker().equals(Constants.REQUEST)){
            holder.workerDoctorForHospital.setTextColor(context.getResources().getColor(R.color.teal_700));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewActivity.class);
                intent.putExtra(Constants.ACTIVITY,Constants.DOCTOR);
                intent.putExtra(Constants.OBJECTS , doctor);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

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
}
