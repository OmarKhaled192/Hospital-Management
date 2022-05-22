package com.yom.hospitalmanagementyom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.model.Disease;
import com.yom.hospitalmanagementyom.model.Drug;

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
    private String type;
    private List<Object> objects;
    public DrugsAdapter(Context context, List<Object> objects,String type){
        this.context=context;
        this.type=type;
        this.objects=objects;

    }
    public DrugsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_disease,parent,false);
        return new DrugsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(type=="D"){
            Drug drug= (Drug) objects.get(position);
            holder.drugName.setText(drug.getNameDrug());
            holder.drugId.setText(drug.getId());

        }

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
