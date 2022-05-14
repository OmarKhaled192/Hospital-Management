package com.yom.hospitalmanagementyom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.model.Recicl;

import java.util.List;

public class Recicladapter extends RecyclerView.Adapter<Recicladapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
   TextView rci1,rci2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rci1=itemView.findViewById(R.id.rci1);
            rci2=itemView.findViewById(R.id.rci2);
        }
    }
    private Context context;
    private List<Recicl> recicls;
    public Recicladapter(Context c, List<Recicl>reciclist){
        this.context=c;
        recicls=reciclist;

    }
    public Recicladapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.itemsrecicl,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Recicladapter.ViewHolder holder, int position) {
Recicl r=recicls.get(position);
holder.rci1.setText(r.getName());
        holder.rci2.setText(r.getId());
    }

    @Override
    public int getItemCount() {
        return recicls.size();
    }
}
