package com.example.appointed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointed.R;
import com.example.appointed.models.Appointment;

import java.util.List;

public class PastAppointmentAdapter extends RecyclerView.Adapter<PastAppointmentAdapter.ViewHolder> {
    List<Appointment> pastList;
    Context context;

    public PastAppointmentAdapter(List<Appointment> pastList){
        this.pastList = pastList;
    }

    @NonNull
    @Override
    public PastAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_past_appointments_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull PastAppointmentAdapter.ViewHolder holder, int position) {
        final Appointment appointment = pastList.get(position);
        holder.textDoctorName.setText(appointment.getDoctor_name());
        holder.textSpecialityName.setText(appointment.getSpeciality_name());
        holder.textStartTime.setText(appointment.getStart_time());
        holder.textEndTime.setText(appointment.getEnd_time());
        holder.view.findViewById(R.id.view_cancelled);
    }


    @Override
    public int getItemCount() {
        return pastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textDoctorName;
        TextView textSpecialityName;
        TextView textStartTime;
        TextView textEndTime;
        View view;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textDoctorName = (TextView)itemView.findViewById(R.id.doctor_name_cancelled_card);
            textSpecialityName = (TextView)itemView.findViewById(R.id.speciality_name_cancelled_card);
            textStartTime = (TextView)itemView.findViewById(R.id.start_time_cancelled_card);
            textEndTime = (TextView)itemView.findViewById(R.id.end_time_cancelled_card);
            view = (View) itemView.findViewById(R.id.view_cancelled);
            cv = (CardView)itemView.findViewById(R.id.cancelled_cv);
        }

    }
}
