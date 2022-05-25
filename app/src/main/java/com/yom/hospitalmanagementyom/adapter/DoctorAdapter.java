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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView doctorName;
        CircleImageView profileDoctorForHospital;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName=itemView.findViewById(R.id.nameDoctorForHospital);
            profileDoctorForHospital=itemView.findViewById(R.id.nameDoctorForHospital);
        }
    }
    private Context context;
    private List<Doctor> doctors;
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
        holder. doctorName.setText(doctor.getName());
        Picasso.with(context).load(doctor.getProfile()).error(R.color.teal_700)
                .into(holder.profileDoctorForHospital);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewActivity.class);
                intent.putExtra(Constants.ACTIVITY,Constants.DOCTOR);
                intent.putExtra(Constants.OBJECT , doctor);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }
}
