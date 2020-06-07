package com.example.appointed.ui.MyUser;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.appointed.R;
import com.example.appointed.activities.ConfirmationActivity;
import com.example.appointed.activities.MainActivity;
import com.example.appointed.models.Patient;
import com.example.appointed.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class MyUserFragment extends Fragment {

    Button btnLogOut;
    private AppBarConfiguration mAppBarConfiguration;
    private MyUserViewModel slideshowViewModel;
    private Patient loggedPatient;
    private View root;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(MyUserViewModel.class);
        root = inflater.inflate(R.layout.fragment_my_user, container, false);
        //final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
                NavigationView navigationView = getActivity().findViewById(R.id.nav_view);

                loggedPatient = (Patient) getActivity().getIntent().getSerializableExtra("loggedPatient");

                TextView namePatient = (TextView) root.findViewById(R.id.textName);
                namePatient.setText(loggedPatient.getName());

                TextView lastNamePatient = (TextView) root.findViewById(R.id.textLastName);
                lastNamePatient.setText(loggedPatient.getLast_name());

                TextView email = (TextView) root.findViewById(R.id.textEmail);
                email.setText(loggedPatient.getEmail());

                TextView number = (TextView) root.findViewById(R.id.textNumber);
                number.setText(loggedPatient.getPhone_number());

                TextView address = (TextView) root.findViewById(R.id.textAddress);
                address.setText(loggedPatient.getAddress());


            }
        });
        return root;
    }
}
