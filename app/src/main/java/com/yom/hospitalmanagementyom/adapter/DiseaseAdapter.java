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

import java.util.List;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView diseaseName,diseasesId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diseaseName=itemView.findViewById(R.id.diseaseName);
            diseasesId=itemView.findViewById(R.id.diseasesId);
        }
    }
    private Context context;
    private List<Disease> diseases;
    public DiseaseAdapter(Context context, List<Disease> diseases){
        this.context=context;
        this.diseases=diseases;

    }
    public DiseaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.item_disease,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseAdapter.ViewHolder holder, int position) {
        Disease disease=diseases.get(position);
        holder.diseaseName.setText(disease.getName());
        holder.diseasesId.setText(disease.getId());
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }
}
