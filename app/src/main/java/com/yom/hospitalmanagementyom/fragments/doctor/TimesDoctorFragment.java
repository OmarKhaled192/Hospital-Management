package com.yom.hospitalmanagementyom.fragments.doctor;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.adapter.TimeAdapter;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.model.Patient;
import com.yom.hospitalmanagementyom.model.Time;
import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class TimesDoctorFragment extends Fragment {

    private List<Time> times;
    private List<Patient> patients;
    private Repository repository;
    private RecyclerView recyclerViewTimes;
    private TimeAdapter timeAdapter;
    public TimesDoctorFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        times=new ArrayList<>();
        patients=new ArrayList<>();
        repository =new Repository(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_times_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Time time=new Time();
        time.setTime("10:22 PM");
        times.add(time);
        times.add(time);
        times.add(time);
        times.add(time);
        times.add(time);

        Patient patient=new Patient();
        patient.setName("JOO");
        patient.setProfile("f");
        patients.add(patient);
        patients.add(patient);
        patients.add(patient);
        patients.add(patient);
        patients.add(patient);

        timeAdapter = new TimeAdapter(requireContext(), times, patients);
        recyclerViewTimes =view.findViewById(R.id.recyclerViewTimes);
        recyclerViewTimes.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewTimes.setAdapter(timeAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper( simpleCallback );
        itemTouchHelper.attachToRecyclerView( recyclerViewTimes );

    }

    Time finishTime = new Time();
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback( 0, ItemTouchHelper.LEFT ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                finishTime = times.get(position);
                times.remove(position);
                timeAdapter.notifyItemRemoved(position);
                Snackbar.make(recyclerViewTimes, "Time Finished.", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                times.add(position, finishTime);
                                timeAdapter.notifyItemInserted(position);
                            }
                        }).show();
            }
        }


        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder( requireContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive )
                    .addSwipeLeftBackgroundColor( ContextCompat.getColor( requireContext(), R.color.teal_700 ) )
                    .addSwipeLeftActionIcon( R.drawable.done )
                    .setActionIconTint( ContextCompat.getColor( recyclerView.getContext(), R.color.white ) )
                    .addSwipeLeftLabel(getString(R.string.finish))
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

}