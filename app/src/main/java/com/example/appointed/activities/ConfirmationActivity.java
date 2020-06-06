package com.example.appointed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appointed.R;
import com.example.appointed.endpoints.AppointmentService;
import com.example.appointed.activities.ConfirmationActivity;
import com.example.appointed.models.Appointment;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmationActivity extends AppCompatActivity {

    private TextView name;
    private TextView address;
    private TextView number;
    private TextView email;
    private TextView dayHour;
    private Button confirmation_button;
    private String namePatient="Juan";
    private String idAppointment="01";
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        name = (TextView)findViewById(R.id.textNameConfirmation);
        //name.setText("1");

        dayHour=(TextView)findViewById(R.id.day_hour);
        //dayHour.setText("2");

        address = (TextView)findViewById(R.id.textAddressConfirmation);
        //address.setText("3");

        number = (TextView)findViewById(R.id.textNumberConfirmation);
        //number.setText("4");

        email = (TextView)findViewById(R.id.textEmailConfirmation);
        //email.setText("5");

        confirmation_button = (Button) findViewById(R.id.confirmation_button);

        confirmation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookedAppointment(namePatient,idAppointment);
            }
        });

    }

    private void bookedAppointment(String namePatient, String idAppointment) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AppointmentService appointmentService = (AppointmentService) retrofit.create(ConfirmationActivity.class);
        Call<Appointment> call =appointmentService.setConfirmedAppointment(namePatient,idAppointment);









    }
}


















