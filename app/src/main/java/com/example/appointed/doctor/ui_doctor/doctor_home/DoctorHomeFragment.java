package com.example.appointed.doctor.ui_doctor.doctor_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appointed.R;
import com.example.appointed.doctor.ui_doctor.doctor_calendar.DoctorsCalendarFragment;


public class DoctorHomeFragment extends Fragment {

    private ImageButton my_calendar_button;

    private DoctorHomeViewModel doctorHomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        doctorHomeViewModel =
                ViewModelProviders.of(this).get(DoctorHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_doctor_home, container, false);
        my_calendar_button = (ImageButton) root.findViewById(R.id.my_calendar_button);
        doctorHomeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

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
        return root;
    }
}
