package com.example.appointed.doctor.ui_doctor.doctor_calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointed.R;
import com.example.appointed.endpoints.AppointmentService;
import com.example.appointed.models.Appointment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoctorsCalendarFragment extends Fragment {
    private View root;
    private TextView testText;
    private DoctorsCalendarViewModel galleryViewModel;
    private DoctorAppointmentAdapter doctorAppointmentAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Appointment> doctorsAppointments = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(DoctorsCalendarViewModel.class);
        root = inflater.inflate(R.layout.fragment_my_calendar, container, false);



        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 4);


        final HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(root, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                getDoctorsAppointments(date);
            }

        });


        return root;
    }

    private void getDoctorsAppointments(Calendar date) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppointmentService aService = retrofit.create(AppointmentService.class);
        Call<List<Appointment>> call = aService.getDoctorsAppointments(2, date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DATE));

        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(response.body()!=null){
                    for(Appointment a : response.body()){
                        doctorsAppointments.add(a);
                    }
                    doctorAppointmentAdapter = new DoctorAppointmentAdapter(doctorsAppointments);

                    recyclerView = (RecyclerView) root.findViewById(R.id.hour_range);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(doctorAppointmentAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {

            }
        });

    }
}
