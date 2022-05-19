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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView drugName;
        CircleImageView circleImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drugName=itemView.findViewById(R.id.nameDoctorForHospital);
            circleImageView=itemView.findViewById(R.id.profileDoctorForHospital);

        }
    }
    private Context context;
    private List<Drug> drugs;
    public DrugsAdapter(Context context, List<Drug> diseases){
        this.context=context;
        this.drugs=drugs;

    }
    public DrugsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_manger,parent,false);
        return new DrugsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drug drug=drugs.get(position);
        holder.drugName.setText(drug.getNameDrug());
        //Picasso.with(context).load(drug.getProfile()).error(R.color.teal_700).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }
}
