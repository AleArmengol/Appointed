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

public class CancelledAppointmentAdapter extends RecyclerView.Adapter<CancelledAppointmentAdapter.ViewHolder> {
    List<Appointment> cancelledList;
    Context context;

    public CancelledAppointmentAdapter(List<Appointment> cancelledList){
        this.cancelledList = cancelledList;
    }

    @NonNull
    @Override
    public CancelledAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cancelled_appointments_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull CancelledAppointmentAdapter.ViewHolder holder, int position) {
        final Appointment appointment = cancelledList.get(position);
        holder.textDoctorName.setText(appointment.getDoctor_name());
        holder.textSpecialityName.setText(appointment.getSpeciality_name());
        holder.textStartTime.setText("Fecha:" +" "+ appointment.getStart_time().substring(0,10));
        holder.textEndTime.setText("De" +" "+ appointment.getStart_time().substring(11,16) + " "+ "A"+" "+appointment.getEnd_time().substring(11,16));
        holder.view.findViewById(R.id.view_cancelled);
    }


    @Override
    public int getItemCount() {
        return cancelledList.size();
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

