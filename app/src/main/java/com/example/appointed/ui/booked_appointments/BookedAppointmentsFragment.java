package com.example.appointed.ui.booked_appointments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appointed.R;
import com.example.appointed.adapters.AppointmentAdapter;
import com.example.appointed.endpoints.AppointmentService;
import com.example.appointed.models.Appointment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookedAppointmentsFragment extends Fragment {


    private View rootView;

    private RecyclerView recyclerView;
    private AppointmentAdapter appointmentAdapter;
    private ArrayList<Appointment> bookedAppointments;


    private BookedAppointmentsViewModel mViewModel;

    public static BookedAppointmentsFragment newInstance() {
        return new BookedAppointmentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(BookedAppointmentsViewModel.class);
        rootView = inflater.inflate(R.layout.fragment_booked_appointments,container,false);

        bookedAppointments = new ArrayList<>();


        this.getAppointments();
        return rootView;
    }

    private void getAppointments() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppointmentService aService = retrofit.create(AppointmentService.class);
        Call<List<Appointment>> call = aService.getBookedAppointments(6, "booked");

        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(response.body()!=null){
                    for(Appointment post : response.body()){
                        bookedAppointments.add(post);
                    }
                    appointmentAdapter = new AppointmentAdapter(bookedAppointments);

                    recyclerView = (RecyclerView) rootView.findViewById(R.id.booked_appointmentsRV);
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


}
