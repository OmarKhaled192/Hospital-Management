package com.yom.hospitalmanagementyom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.model.Hospital;
import java.util.List;

public class HospitalViewAdapter extends RecyclerView.Adapter<HospitalViewAdapter.HospitalHolder> {
    private final Context context;
    private final List<Hospital> hospitals;

    public HospitalViewAdapter(Context context, List<Hospital> hospitals){
        this.context=context;
        this.hospitals=hospitals;
    }

    @NonNull
    @Override
    public HospitalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital_view,parent,false);
        return new HospitalHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalHolder holder, int position) {
        Hospital hospital=hospitals.get(position);
        holder.nameHospital.setText(hospital.getName());
        Picasso.with(context).load(hospital.getProfile()).error(R.color.white).into(holder.profileHospital);
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }


    static class HospitalHolder extends RecyclerView.ViewHolder{
        ImageView profileHospital;
        TextView nameHospital;
        public HospitalHolder(@NonNull View itemView) {
            super(itemView);
            profileHospital=itemView.findViewById(R.id.profileHospital);
            nameHospital=itemView.findViewById(R.id.nameHospital);
        }
    }
}
