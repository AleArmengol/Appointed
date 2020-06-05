package com.example.appointed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appointed.R;
import com.example.appointed.endpoints.DoctorService;
import com.example.appointed.models.Doctor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewAppointmentActivity extends AppCompatActivity {

    private Spinner doctor_spinner;
    private Spinner day_spinner;
    private Spinner hour_spinner;
    private ImageView female_doctor;
    private ImageView male_doctor;
    private Button next_button;


    ArrayAdapter<String> spinnerArrayAdapter;
    ArrayList<String> doctors = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        spinnerArrayAdapter = new ArrayAdapter<>(NewAppointmentActivity.this,android.R.layout.simple_spinner_item,doctors);

        female_doctor = new ImageView(this);
        male_doctor = new ImageView(this);
        doctor_spinner = (Spinner) findViewById(R.id.doctor_spinner);
        day_spinner = (Spinner) findViewById(R.id.day_spinner);
        hour_spinner = (Spinner) findViewById(R.id.hour_spinner);
        next_button = (Button) findViewById(R.id.next_button);
        doctors.add("Seleccione un profesional ...");
        addItemsOnDoctorSpinner();
        doctor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void addItemsOnDoctorSpinner() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final DoctorService doctorService = retrofit.create(DoctorService.class);
        Call<List<Doctor>> call = doctorService.getDoctorsBySpeciality(12);

        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {

                if(response.body() != null){
                    for(Doctor d : response.body()){
                        doctors.add(d.getName() + " " + d.getLast_name());
                    }
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    doctor_spinner.setAdapter(spinnerArrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
