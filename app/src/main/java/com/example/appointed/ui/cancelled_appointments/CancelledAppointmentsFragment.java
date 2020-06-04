package com.example.appointed.ui.cancelled_appointments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appointed.R;
import com.example.appointed.endpoints.AppointmentService;
import com.example.appointed.models.Appointment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CancelledAppointmentsFragment extends Fragment {

    private TextView message;
    private ListView cancelledAppointmentsList;
    private ArrayList<String> cancelled_appointments;
    private ArrayAdapter<String> adapter;
    private View rootView;



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
        message = (TextView) rootView.findViewById(R.id.message);
        cancelled_appointments = new ArrayList<>();
        cancelledAppointmentsList = (ListView)rootView.findViewById(R.id.cancelledAppointmentsList);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,cancelled_appointments);
        cancelledAppointmentsList.setAdapter(adapter);

        this.getAppointments();
        return rootView;
    }

    private void getAppointments() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppointmentService aService = retrofit.create(AppointmentService.class);
        Call<List<Appointment>> call = aService.getCancelledAppointments(6, "cancelled");

        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(response.body() != null){
                    for(Appointment post : response.body()){
                        cancelled_appointments.add(String.valueOf(post.getDoctor_name()));
                        cancelled_appointments.add(String.valueOf(post.getSpeciality_name()));
                        cancelled_appointments.add(String.valueOf(post.getStart_time()));
                        cancelled_appointments.add(String.valueOf(post.getEnd_time()));
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    message.setText("No me trajo nada");
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                message.setText(t.getMessage());
            }
        });
    }

}
