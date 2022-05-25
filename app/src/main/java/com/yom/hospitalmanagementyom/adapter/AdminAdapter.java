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
import com.yom.hospitalmanagementyom.model.Admin;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView adminName;
        CircleImageView profileAdminForHospital;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adminName=itemView.findViewById(R.id.nameDoctorForHospital);
            profileAdminForHospital=itemView.findViewById(R.id.nameDoctorForHospital);
        }
    }
    private Context context;
    private List<Admin> admins;
    public AdminAdapter(Context context, List<Admin> admins){
        this.context=context;
        this.admins=admins;
    }

    @NonNull
    public AdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_manger,parent,false);
        return new AdminAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Admin admin= admins.get(position);
        holder. adminName.setText(admin.getName());
        Picasso.with(context).load(admin.getProfile()).error(R.color.teal_700)
                .into(holder.profileAdminForHospital);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewActivity.class);
                intent.putExtra(Constants.ACTIVITY,Constants.ADMIN);
                intent.putExtra(Constants.OBJECT , admin);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return admins.size();
    }
}
