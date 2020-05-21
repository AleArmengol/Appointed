package com.example.appointed.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appointed.R;
import com.example.appointed.ui.booked_appointments.BookedAppointmentsFragment;
import com.example.appointed.ui.cancelled_appointments.CancelledAppointmentsFragment;
import com.example.appointed.ui.past_appointments.PastAppointmentsFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        Button cancelled_appointments_button = (Button)rootView.findViewById(R.id.cancelled_appointments_button);
        Button past_appointments_button = (Button)rootView.findViewById(R.id.past_appointments_button);
        Button booked_appointments_button = (Button)rootView.findViewById(R.id.booked_appointments_button);


        cancelled_appointments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelledAppointmentsFragment cafr = new CancelledAppointmentsFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,cafr).commit();
            }
        });

        booked_appointments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookedAppointmentsFragment bafr = new BookedAppointmentsFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,bafr).commit();
            }
        });

        past_appointments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PastAppointmentsFragment pafr = new PastAppointmentsFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,pafr).commit();
            }
        });


        return rootView;
    }
}
