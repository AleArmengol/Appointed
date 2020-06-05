package com.example.appointed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointed.R;
import com.example.appointed.models.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    List<Appointment> appointmentList;
    Context context;

    public AppointmentAdapter(List<Appointment> appointmentList){
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        holder.textDoctorName.setText(appointment.getDoctor_name());
        holder.textSpecialityName.setText(appointment.getSpeciality_name());
        holder.textStartTime.setText(appointment.getStart_time());
        holder.textEndTime.setText(appointment.getEnd_time());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textDoctorName;
        TextView textSpecialityName;
        TextView textStartTime;
        TextView textEndTime;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textDoctorName = (TextView)itemView.findViewById(R.id.doctor_name_card);
            textSpecialityName = (TextView)itemView.findViewById(R.id.speciality_name_card);
            textStartTime = (TextView)itemView.findViewById(R.id.start_time_card);
            textEndTime = (TextView)itemView.findViewById(R.id.end_time_card);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }

    }
}
