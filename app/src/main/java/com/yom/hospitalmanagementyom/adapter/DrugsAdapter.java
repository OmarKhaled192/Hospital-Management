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
import com.yom.hospitalmanagementyom.model.Disease;
import com.yom.hospitalmanagementyom.model.Drug;

import java.util.ArrayList;
import java.util.List;

public class DrugsAdapter extends RecyclerView.Adapter<DrugsAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView drugName,drugId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drugName=itemView.findViewById(R.id.diseaseName);
            drugId=itemView.findViewById(R.id.diseasesId);
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
        View v= LayoutInflater.from(context).inflate(R.layout.item_disease,parent,false);
        return new DrugsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drug drug= drugs.get(position);
        holder.drugName.setText(drug.getNameDrug());
        holder.drugId.setText(drug.getId());
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
