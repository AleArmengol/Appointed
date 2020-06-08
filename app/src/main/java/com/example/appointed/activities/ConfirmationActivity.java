package com.example.appointed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appointed.R;
import com.example.appointed.endpoints.AppointmentService;
import com.example.appointed.models.Appointment;
import com.example.appointed.models.Doctor;
import com.example.appointed.models.Patient;
import com.example.appointed.network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmationActivity extends AppCompatActivity {


    private TextView name;
    private TextView address;
    private TextView number;
    private TextView email;
    private TextView dayHour;
    private Button confirmation_button;
    private Button cancel_button;
    private String namePatient;
    private ImageView imageMap;
    int idAppointment;
    int idPatient;
    Retrofit retrofit;

    private Doctor doctorSelected;
    private Appointment appointmentSelected;


    Patient loggedPatient;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        doctorSelected= (Doctor) getIntent().getSerializableExtra("Doctor");
        appointmentSelected= (Appointment) getIntent().getSerializableExtra("Appointment");
        loggedPatient = (Patient) getIntent().getSerializableExtra("loggedPatient");

        namePatient = loggedPatient.getName()+" "+loggedPatient.getLast_name();
        idAppointment=appointmentSelected.getId();
        idPatient=loggedPatient.getId();

        name = (TextView)findViewById(R.id.textNameConfirmation);
        name.setText("Doctor: "+doctorSelected.getName()+" "+doctorSelected.getLast_name());

        dayHour=(TextView)findViewById(R.id.day_hour);
        String startTime=appointmentSelected.getStart_time();
        String day =startTime.substring(0,10);
        String hour = startTime.substring(11,13);
        String min = startTime.substring(14,16);
        String completeHour = hour + ":" + min;
        dayHour.setText("Dia: "+day+"     Hora: "+completeHour);

        address = (TextView)findViewById(R.id.textAddressConfirmation);
        //address.setText("Direccion: " + doctorSelected.getAddress()); Av. Pueyrredón 1640, C1118 AAT, Buenos Aires
        address.setText("Direccion: " +" Av. Pueyrredón 1640");

        number = (TextView)findViewById(R.id.textNumberConfirmation);
        number.setText("Telefono: "+ doctorSelected.getPhone_number());

        email = (TextView)findViewById(R.id.textEmailConfirmation);
        email.setText("Mail: "+doctorSelected.getEmail());

        confirmation_button = (Button) findViewById(R.id.confirmation_button);
        cancel_button =(Button) findViewById(R.id.cancel_button);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCancel = new Intent(ConfirmationActivity.this, SpecialityActivity.class);
                intentCancel.putExtra("loggedPatient", loggedPatient);
                startActivity(intentCancel);
            }
        });

        confirmation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookedAppointment(namePatient,idAppointment,idPatient);
            }
        });


        imageMap = (ImageView) findViewById(R.id.imageMap);
        imageMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMap = new Intent(ConfirmationActivity.this,MapsActivity.class);
                startActivity(goToMap);
            }
        });

    }

    private void bookedAppointment(String namePatient, int idAppointment, int idPatient) {

        retrofit = RetrofitClientInstance.getRetrofitInstance();

        AppointmentService appointmentService = retrofit.create(AppointmentService.class);
        Call<Appointment> call =appointmentService.updateConfirmedAppointment(idAppointment,namePatient,idPatient);

        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(response.body()!=null){
                    Log.d("Funciono","funciono");
                    Intent patientHomeActivity = new Intent(ConfirmationActivity.this, PatientHome.class);
                    patientHomeActivity.putExtra("loggedPatient", loggedPatient);
                    patientHomeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(patientHomeActivity);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {

            }
        });









    }
}


















