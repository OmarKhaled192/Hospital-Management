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
import com.yom.hospitalmanagementyom.model.Patient;
import com.yom.hospitalmanagementyom.model.Time;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder>{

    private final List<Time> times;
    private final List<Patient> patients;
    private final Context context;

    public TimeAdapter(Context context, List<Time> times, List<Patient> patients) {
        this.context = context;
        this.times = times;
        this.patients = patients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_time, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Time time = times.get( position );
        Patient patient = patients.get(position);

        Picasso.with( context ).load( patient.getProfile() )
                .placeholder( R.color.teal_700 ).into( holder.profilePatient );

        holder.namePatient.setText(patient.getName());
        holder.time.setText(time.getTime());
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profilePatient;
        TextView namePatient, time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePatient = itemView.findViewById( R.id.profilePatient );
            namePatient = itemView.findViewById( R.id.namePatient );
            time = itemView.findViewById( R.id.time );
        }
    }
}
