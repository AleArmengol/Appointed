package com.example.appointed.ui.booked_appointments;

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

public class BookedAppointmentsFragment extends Fragment {

    private TextView messageB;
    private ListView bookedAppointmentsList;
    private ArrayList<String> booked_appointments;
    private ArrayAdapter<String> adapter;
    private View rootView;


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
        messageB = (TextView) rootView.findViewById(R.id.messageB);
        booked_appointments = new ArrayList<>();
        bookedAppointmentsList = (ListView) rootView.findViewById(R.id.bookedAppointmentsList);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,booked_appointments);
        bookedAppointmentsList.setAdapter(adapter);
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
                        booked_appointments.add(String.valueOf(post.getDoctor_name()));
                        booked_appointments.add(String.valueOf(post.getSpeciality_name()));
                        booked_appointments.add(String.valueOf(post.getStart_time()));
                        booked_appointments.add(String.valueOf(post.getEnd_time()));
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    messageB.setText("No me trajo nada");
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                messageB.setText(t.getMessage());
            }
        });
    }


}
