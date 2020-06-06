package com.example.appointed.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.appointed.R;
import com.example.appointed.activities.ConfirmationActivity;
import com.example.appointed.activities.SpecialityActivity;
import com.example.appointed.models.Patient;
import com.example.appointed.ui.booked_appointments.BookedAppointmentsFragment;
import com.example.appointed.ui.cancelled_appointments.CancelledAppointmentsFragment;
import com.example.appointed.ui.past_appointments.PastAppointmentsFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Patient loggedPatient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton cancelled_appointments_button = (ImageButton)rootView.findViewById(R.id.cancelled_appointments_button);
        ImageButton past_appointments_button = (ImageButton)rootView.findViewById(R.id.past_appointments_button);
        ImageButton booked_appointments_button = (ImageButton)rootView.findViewById(R.id.booked_appointments_button);
        Button new_appointment_button  = (Button)rootView.findViewById(R.id.new_appointment_button);
        final AlertDialog.Builder payment = new AlertDialog.Builder(getActivity());



        loggedPatient = (Patient) getActivity().getIntent().getSerializableExtra("loggedPatient");

        new_appointment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loggedPatient.isPayment_uptodate()) {
                    Intent specialityActivity = new Intent(getActivity(), SpecialityActivity.class);
                    specialityActivity.putExtra("loggedPatient", loggedPatient);
                    //patientHomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  DEJAR ESTA LINEA COMENTADA
                    startActivity(specialityActivity);

                }
                else{
                    payment.setCancelable(true);
                    payment.setTitle("ERROR");
                    payment.setMessage("Usted no podrá sacar un turno hasta que se encuentre al día con los pagos");
//                    LayoutInflater image = LayoutInflater.from(getActivity());
//                    final View view = image.inflate(R.layout.error_pop_up, null);
                    payment.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
//                    payment.setView(view);
                    AlertDialog dialog = payment.create();
                    dialog.show();
                }
            }
        });

        cancelled_appointments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelledAppointmentsFragment cafr = new CancelledAppointmentsFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,cafr).addToBackStack(null).commit();
            }
        });

        booked_appointments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookedAppointmentsFragment bafr = new BookedAppointmentsFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,bafr).addToBackStack(null).commit();
            }
        });

        past_appointments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PastAppointmentsFragment pafr = new PastAppointmentsFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,pafr).addToBackStack(null).commit();
            }
        });


        return rootView;
    }
}
