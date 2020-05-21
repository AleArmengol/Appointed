package com.example.appointed.ui.newAppointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appointed.R;

public class NewAppointmentFragment extends Fragment {

    private NewAppointmentViewModel newAppointmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newAppointmentViewModel =
                ViewModelProviders.of(this).get(NewAppointmentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_new_appointment, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        newAppointmentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
