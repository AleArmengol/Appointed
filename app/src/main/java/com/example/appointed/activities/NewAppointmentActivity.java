package com.example.appointed.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointed.R;
import com.example.appointed.endpoints.AppointmentService;
import com.example.appointed.endpoints.DoctorService;
import com.example.appointed.models.Appointment;
import com.example.appointed.models.DateAppointment;
import com.example.appointed.models.Doctor;
import com.example.appointed.models.Patient;
import com.example.appointed.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewAppointmentActivity extends AppCompatActivity {

    Patient loggedPatient;

    private Spinner doctor_spinner;
    private Spinner day_spinner;
    private Spinner hour_spinner;
    private ImageView female_doctor;
    private ImageView male_doctor;
    private ImageView goToCalendar;
    private ImageView displayHours;
    private Button next_button;
    private int specialityId;
    private String specialityName;
    private Doctor doctorSelected;
    private Integer selectedDay = null;
    private Integer selectedMonth = null;
    private Integer selectedYear = null;
    private DateAppointment dateAppointment;
    private TextView selectDateTxt;
    private boolean isDateSelected = false;
    private boolean isDoctorSelected = false;
    private boolean isAppointmentSelected = false;
    private Retrofit retrofit;
    private Appointment selectedAppointment;


    ArrayAdapter<String> doctorSpinnerArrayAdapter;
    ArrayAdapter<String> hoursSpinnerArrayAdapter;
    ArrayList<String> doctor_names = new ArrayList<>();
    ArrayList<String> hoursList = new ArrayList<>();
    ArrayList<Doctor> doctors  = new ArrayList<>();
    ArrayList<Appointment> appointments  = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);
        doctorSpinnerArrayAdapter = new ArrayAdapter<>(NewAppointmentActivity.this,android.R.layout.simple_spinner_item, doctor_names);
        hoursSpinnerArrayAdapter = new ArrayAdapter<>(NewAppointmentActivity.this,android.R.layout.simple_spinner_item, hoursList);
        goToCalendar = (ImageView) findViewById(R.id.goToCalendar);
        displayHours = (ImageView) findViewById(R.id.displayHours);
        female_doctor = new ImageView(this);
        male_doctor = new ImageView(this);
        doctor_spinner = (Spinner) findViewById(R.id.doctor_spinner);
        day_spinner = (Spinner) findViewById(R.id.day_spinner);
        selectDateTxt = (TextView) findViewById(R.id.selectDateTxt);
        hour_spinner = (Spinner) findViewById(R.id.hour_spinner);
        hour_spinner.setAlpha(0.3f);
        hour_spinner.setClickable(false);
        hour_spinner.setEnabled(false);
        hoursList.add("Seleccione una hora");
        hoursSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hour_spinner.setAdapter(hoursSpinnerArrayAdapter);
        next_button = (Button) findViewById(R.id.next_button);

        loggedPatient = (Patient) getIntent().getSerializableExtra("loggedPatient");

        specialityId = getIntent().getIntExtra("specialityId", 0);
        specialityName = getIntent().getStringExtra("specialityName");
        doctor_names.add("Seleccione un profesional ...");
        addItemsOnDoctorSpinner();

        goToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendarIntent = new Intent(NewAppointmentActivity.this, CalendarActivity.class);
                calendarIntent.putExtra("specialityId", specialityId);
                calendarIntent.putExtra("specialityName", specialityName);
                if(doctorSelected != null){
                    calendarIntent.putExtra("doctorId", doctorSelected.getId());
                }
                startActivityForResult(calendarIntent, 1);
            }
        });

        hour_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    selectedAppointment = appointments.get(position - 1);
                    isAppointmentSelected = true;
                    if(isDateSelected && isDoctorSelected && isAppointmentSelected){
                        next_button.setAlpha(1f);
                        next_button.setClickable(true);
                        next_button.setEnabled(true);
                    }
                }
                else {
                    next_button.setAlpha(0.3f);
                    next_button.setClickable(false);
                    next_button.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {//ACA
            @Override
            public void onClick(View v) {
                //namePatient = getIntent().getStringExtra("namePatient");

                Intent intentDoctor_Appointment = new Intent(NewAppointmentActivity.this, ConfirmationActivity.class);
                intentDoctor_Appointment.putExtra("Doctor",  doctorSelected);
                intentDoctor_Appointment.putExtra("Appointment",selectedAppointment);
                intentDoctor_Appointment.putExtra("loggedPatient", loggedPatient);

                startActivity(intentDoctor_Appointment);



            }
        });

        doctor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0) {
                    doctorSelected = doctors.get(i - 1);
                    isDoctorSelected = true;
                    if(isDateSelected && isDateSelected) {
                        enableHourSpinner();
                    }
                }
                else{
                    doctorSelected = null;
                    isDoctorSelected = false;
                    hour_spinner.setAlpha(0.3f);
                    hour_spinner.setClickable(false);
                    hour_spinner.setEnabled(false);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void populateSpinnerHours() {
        hoursList.clear();
        hoursList.add("Seleccione una hora");
        retrofit = RetrofitClientInstance.getRetrofitInstance();
        AppointmentService appointmentService = retrofit.create(AppointmentService.class);
        Call<List<Appointment>> call = appointmentService.getAppointments(doctorSelected.getId(), specialityName, selectedDay, selectedMonth, selectedYear);

        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(android.os.Debug.isDebuggerConnected()){
                    android.os.Debug.waitForDebugger();
                }
                if(response.body() != null){
                    for(Appointment appointment : response.body()){
                        appointments.add(appointment);
                        String startTime = appointment.getStart_time();
                        String hour = startTime.substring(11,13);
                        String min = startTime.substring(14,16);
                        String completeHour = hour + ":" + min;
                        hoursList.add(completeHour);

                    }
                    hoursSpinnerArrayAdapter.getCount();
                    hoursSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hour_spinner.setAdapter(hoursSpinnerArrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {

            }
        });
    }

    private void enableHourSpinner() {
        hour_spinner.setClickable(true);
        hour_spinner.setEnabled(true);
        hour_spinner.setAlpha(1f);
        populateSpinnerHours();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
             selectedDay = data.getIntExtra("day", 0);
             selectedMonth = data.getIntExtra("month", 0);
             selectedYear = data.getIntExtra("year", 0);
            String fullDate = selectedDay.toString() + "/" + selectedMonth.toString() + "/" + selectedYear.toString();
            isDateSelected = true;
            selectDateTxt.setText(fullDate);

            if(isDateSelected && isDoctorSelected){
                enableHourSpinner();
            }
            if(!isDoctorSelected){
                addItemsOnDoctorSpinner();
            }

        }
    }

    public void addItemsOnDoctorSpinner() {
        doctor_names.clear();
        doctor_names.add("Seleccione profesional");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final DoctorService doctorService = retrofit.create(DoctorService.class);
        Call<List<Doctor>> call = doctorService.getDoctorsBySpeciality(specialityId, selectedDay, selectedMonth, selectedYear);

        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {

                if(response.body() != null){
                    for(Doctor d : response.body()){
                        doctor_names.add(d.getName() + " " + d.getLast_name());
                        doctors.add(d);
                    }
                    doctorSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    doctor_spinner.setAdapter(doctorSpinnerArrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
