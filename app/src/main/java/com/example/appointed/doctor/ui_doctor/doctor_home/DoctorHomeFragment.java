package com.example.appointed.doctor.ui_doctor.doctor_home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appointed.R;
import com.example.appointed.activities.EditCalendarActivity;
import com.example.appointed.activities.SpecialityActivity;
import com.example.appointed.doctor.ui_doctor.doctor_calendar.DoctorsCalendarFragment;

import com.example.appointed.doctor.ui_doctor.doctor_cancelled_appointments.DoctorCancelledAppointmentsFragment;

import com.example.appointed.models.Doctor;
import com.example.appointed.models.Patient;



public class DoctorHomeFragment extends Fragment {

    private ImageButton my_calendar_button;
    private ImageButton cancelled_appointments_doctor;

    private DoctorHomeViewModel doctorHomeViewModel;

    private Button edit_calendar_button;

    private Doctor loggedDoctor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        doctorHomeViewModel =
                ViewModelProviders.of(this).get(DoctorHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_doctor_home, container, false);
        my_calendar_button = (ImageButton) root.findViewById(R.id.my_calendar_button);

        cancelled_appointments_doctor = (ImageButton) root.findViewById(R.id.cancelled_appointments_doctor);

        loggedDoctor = (Doctor) getActivity().getIntent().getSerializableExtra("loggedDoctor");
        edit_calendar_button = (Button) root.findViewById(R.id.edit_calendar_button);

        doctorHomeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        edit_calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editCalendarActivity = new Intent(getActivity(), EditCalendarActivity.class);
                editCalendarActivity.putExtra("loggedDoctor", loggedDoctor);
                startActivity(editCalendarActivity);
            }
        });

        my_calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorsCalendarFragment dcfr = new DoctorsCalendarFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.doctor_nav_host_fragment,dcfr).addToBackStack(null).commit();
            }
        });

        cancelled_appointments_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorCancelledAppointmentsFragment dcafr = new DoctorCancelledAppointmentsFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.doctor_nav_host_fragment,dcafr).addToBackStack(null).commit();
            }
        });
        return root;
    }
}
