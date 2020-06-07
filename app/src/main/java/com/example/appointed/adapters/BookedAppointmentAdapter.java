package com.example.appointed.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointed.R;
import com.example.appointed.activities.PatientHome;
import com.example.appointed.endpoints.AppointmentService;
import com.example.appointed.models.Appointment;
import com.example.appointed.network.RetrofitClientInstance;
import com.example.appointed.ui.login.LoginActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookedAppointmentAdapter extends RecyclerView.Adapter<BookedAppointmentAdapter.ViewHolder> {
    Retrofit retrofit;
    List<Appointment> appointmentList;
    Context context;

    public BookedAppointmentAdapter(List<Appointment> appointmentList){
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public BookedAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull BookedAppointmentAdapter.ViewHolder holder, final int position) {
        final Appointment appointment = appointmentList.get(position);
        holder.cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getRootView().getContext());
                alert.setCancelable(true);
                alert.setTitle("Atención");
                alert.setMessage("Al aceptar, usted cancelará el turno seleccionado, desea continuar ?");
                alert.setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Appointment appointmentC = appointmentList.get(position);
                                retrofit = RetrofitClientInstance.getRetrofitInstance();
                                AppointmentService aService = retrofit.create(AppointmentService.class);
                                Call<Appointment> call = aService.updateStatus(appointmentC.getId());
                                call.enqueue(new Callback<Appointment>() {
                                    @Override
                                    public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                                        if(response.body()!=null){
                                            appointmentList.remove(appointmentC);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Appointment> call, Throwable t) {

                                    }
                                });
                                String itemLabel = String.valueOf(appointmentList.get(position));
                                appointmentList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,appointmentList.size());
                                Toast.makeText(context, "Removed :" + itemLabel,Toast.LENGTH_SHORT).show();
                            }
                        });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
        holder.textDoctorName.setText(appointment.getDoctor_name());
        holder.textSpecialityName.setText(appointment.getSpeciality_name());
        holder.textStartTime.setText("Fecha:" +" "+ appointment.getStart_time().substring(0,10));
        holder.textEndTime.setText("De" +" "+ appointment.getStart_time().substring(11,16) + " "+ "A"+" "+appointment.getEnd_time().substring(11,16));
        holder.view.findViewById(R.id.view_cancelled);
    }


    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageButton cancel_button;
        TextView textDoctorName;
        TextView textSpecialityName;
        TextView textStartTime;
        TextView textEndTime;
        View view;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            cancel_button = (ImageButton) itemView.findViewById(R.id.cancel_button);
            textDoctorName = (TextView)itemView.findViewById(R.id.doctor_name_cancelled_card);
            textSpecialityName = (TextView)itemView.findViewById(R.id.speciality_name_cancelled_card);
            textStartTime = (TextView)itemView.findViewById(R.id.start_time_cancelled_card);
            textEndTime = (TextView)itemView.findViewById(R.id.end_time_cancelled_card);
            view = (View) itemView.findViewById(R.id.view_cancelled);
            cv = (CardView)itemView.findViewById(R.id.cancelled_cv);

        }

    }
}
