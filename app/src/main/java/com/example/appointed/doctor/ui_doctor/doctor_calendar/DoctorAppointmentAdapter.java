package com.example.appointed.doctor.ui_doctor.doctor_calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointed.R;
import com.example.appointed.models.Appointment;

import java.util.ArrayList;
import java.util.List;


class DoctorAppointmentAdapter extends RecyclerView.Adapter <DoctorAppointmentAdapter.ViewHolder> {
    private List<Appointment> doctorsAppointments;
    Context context;
    public DoctorAppointmentAdapter(ArrayList<Appointment> doctorsAppointments) {
        this.doctorsAppointments = doctorsAppointments;
    }


    @NonNull
    @Override
    public DoctorAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_doctors_appointments,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAppointmentAdapter.ViewHolder holder, int position) {
        Appointment appointment = doctorsAppointments.get(position);
        holder.start_and_end.setText("De " + appointment.getStart_time().substring(11,16) + " a " + appointment.getEnd_time().substring(11,16));
        holder.speciality.setText(appointment.getSpeciality_name());
        holder.patient.setText(appointment.getPatient_name());
        holder.status.setText(appointment.getStatus());
    }

    @Override
    public int getItemCount() {
        return doctorsAppointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView start_and_end;
        TextView speciality;
        TextView patient;
        TextView status;

        public ViewHolder(View itemView)
        {
            super(itemView);
            start_and_end = (TextView)itemView.findViewById(R.id.start_and_end);
            speciality = (TextView)itemView.findViewById(R.id.speciality);
            patient = (TextView)itemView.findViewById(R.id.patient);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
