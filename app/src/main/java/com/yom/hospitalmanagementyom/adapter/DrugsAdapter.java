package com.yom.hospitalmanagementyom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.model.Disease;
import com.yom.hospitalmanagementyom.model.Drug;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DrugsAdapter extends RecyclerView.Adapter<DrugsAdapter.ViewHolder>{


    private Context context;
    private List<Object> drugs;
    String type;
    public DrugsAdapter(Context context, List<Object> diseases,String type){
        this.context=context;
        this.drugs=drugs;
        this.type=type;

    }
    public DrugsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_manger,parent,false);
        return new DrugsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(type.equals("D")){
            Drug drug= (Drug) drugs.get(position);
            holder.nameDoctorForHospital.setText(drug.getNameDrug());
        }

        //Picasso.with(context).load(drug.getProfile()).error(R.color.teal_700).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameDoctorForHospital;
        CircleImageView profileDoctorForHospital;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDoctorForHospital=itemView.findViewById(R.id.nameDoctorForHospital);
            profileDoctorForHospital=itemView.findViewById(R.id.profileDoctorForHospital);

        }
    }
}
