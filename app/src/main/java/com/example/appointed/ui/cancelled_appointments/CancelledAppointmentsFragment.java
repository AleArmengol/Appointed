package com.example.appointed.ui.cancelled_appointments;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appointed.R;
import com.example.appointed.adapters.CancelledAppointmentAdapter;
import com.example.appointed.endpoints.AppointmentService;
import com.example.appointed.models.Appointment;
import com.example.appointed.models.Patient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CancelledAppointmentsFragment extends Fragment {


    private View rootView;
    private RecyclerView recyclerView;
    private ArrayList<Appointment> cancelledAppointments;
    private CancelledAppointmentAdapter cancelledAdapter;




    private CancelledAppointmentsViewModel mViewModel;

    public static CancelledAppointmentsFragment newInstance() {
        return new CancelledAppointmentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(CancelledAppointmentsViewModel.class);

        rootView = inflater.inflate(R.layout.cancelled_appointments_fragment,container,false);
        cancelledAppointments = new ArrayList<>();


        this.getAppointments();
        return rootView;
    }

    private void getAppointments() {
        Patient loggedPatient = (Patient) getActivity().getIntent().getSerializableExtra("loggedPatient");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppointmentService aService = retrofit.create(AppointmentService.class);
        Call<List<Appointment>> call = aService.getCancelledAppointments(loggedPatient.getId(), "cancelled");

        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(response.body() != null){
                    for(Appointment post : response.body()){
                        cancelledAppointments.add(post);
                    }
                    cancelledAdapter = new CancelledAppointmentAdapter(cancelledAppointments);

                    recyclerView = (RecyclerView) rootView.findViewById(R.id.booked_appointmentsRV);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(cancelledAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
            }
        });
    }

}
