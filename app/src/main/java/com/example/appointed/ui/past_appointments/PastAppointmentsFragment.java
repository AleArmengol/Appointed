package com.example.appointed.ui.past_appointments;

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
import com.example.appointed.adapters.PastAppointmentAdapter;
import com.example.appointed.endpoints.AppointmentService;
import com.example.appointed.models.Appointment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PastAppointmentsFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private PastAppointmentAdapter pastAdapter;
    private ArrayList<Appointment> pastAppointments;




    private PastAppointmentsViewModel mViewModel;

    public static PastAppointmentsFragment newInstance() {
        return new PastAppointmentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(PastAppointmentsViewModel.class);

        rootView = inflater.inflate(R.layout.fragment_past_appointments,container,false);
        pastAppointments = new ArrayList<>();


        this.getAppointments();
        return rootView;
    }

    private void getAppointments() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppointmentService aService = retrofit.create(AppointmentService.class);
        Call<List<Appointment>> call = aService.getPastAppointments(6,"past");

        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(response.body() != null){
                    for(Appointment post : response.body()){
                        pastAppointments.add(post);
                    }
                    pastAdapter = new PastAppointmentAdapter(pastAppointments);

                    recyclerView = (RecyclerView) rootView.findViewById(R.id.booked_appointmentsRV);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(pastAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
            }
        });
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(PastAppointmentsViewModel.class);
//        // TODO: Use the ViewModel
//    }

}
