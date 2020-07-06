package com.example.appointed.doctor.ui_doctor.doctor_cancelled_appointments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appointed.R;
import com.example.appointed.adapters.BookedAppointmentAdapter;
import com.example.appointed.doctor.adapters.DoctorCancelledAppointmentsAdapter;
import com.example.appointed.endpoints.AppointmentService;
import com.example.appointed.models.Appointment;
import com.example.appointed.models.Doctor;
import com.example.appointed.models.Patient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoctorCancelledAppointmentsFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private DoctorCancelledAppointmentsAdapter appointmentAdapter;
    private ArrayList<Appointment> cancelledAppointments;
//    private Doctor loggedDoctor;

    private DoctorCancelledAppointmentsViewModel mViewModel;

    public static DoctorCancelledAppointmentsFragment newInstance() {
        return new DoctorCancelledAppointmentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.doctor_cancelled_appointments_fragment, container, false);
        cancelledAppointments = new ArrayList<>();


        this.getCancelledAppointments();
        return rootView;
    }

    private void getCancelledAppointments() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppointmentService aService = retrofit.create(AppointmentService.class);
        Call<List<Appointment>> call = aService.getCancelledAppointments(2);

        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(response.body()!= null){
                    for(Appointment a : response.body()){
                        cancelledAppointments.add(a);
                    }
                    appointmentAdapter = new DoctorCancelledAppointmentsAdapter(cancelledAppointments);

                    recyclerView = (RecyclerView)rootView.findViewById(R.id.cancelled_appointments_doctorRV);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(appointmentAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DoctorCancelledAppointmentsViewModel.class);
        // TODO: Use the ViewModel
    }

}
