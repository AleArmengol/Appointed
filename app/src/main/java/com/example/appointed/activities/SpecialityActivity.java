package com.example.appointed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.appointed.R;
import com.example.appointed.models.Patient;
import com.example.appointed.models.Speciality;

import java.util.ArrayList;
import java.util.List;

import com.example.appointed.endpoints.SpecialityService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecialityActivity extends AppCompatActivity {

    Spinner spinnerEspecialidad;
    ArrayList<String> specialityNames = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Button accept_button;
    Patient loggedPatient;
    ArrayList<Speciality> specialities  = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String specialitySelected;
    int idSpecialitySelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speciality);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, specialityNames);
        specialityNames.add("Seleccione Especialidad...");
        spinnerEspecialidad= (Spinner) findViewById(R.id.Especialidad);
        accept_button = (Button) findViewById(R.id.accept_button);
        loggedPatient = (Patient) getIntent().getSerializableExtra("loggedPatient");
        addSpecialityOnSpinner();
        spinnerEspecialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    specialitySelected = adapterView.getItemAtPosition(i).toString();
                    idSpecialitySelected = specialities.get(i-1).getId();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNewAppointment = new Intent(SpecialityActivity.this, NewAppointmentActivity.class);
                intentNewAppointment.putExtra("specialityId", idSpecialitySelected);
                intentNewAppointment.putExtra("specialityName", specialitySelected);
                startActivity(intentNewAppointment);
            }
        });


    }

    private void addSpecialityOnSpinner() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/").addConverterFactory(GsonConverterFactory.create()).build();

        SpecialityService sService =retrofit.create(SpecialityService.class);
        Call<List<Speciality>> call =sService.getResponse();

        call.enqueue(new Callback<List<Speciality>>() {
            @Override
            public void onResponse(Call<List<Speciality>> call, Response<List<Speciality>> response) {
                if(android.os.Debug.isDebuggerConnected()){
                    android.os.Debug.waitForDebugger();
                }
                if(response.body()!=null){
                    for(Speciality s:response.body()){

                        specialityNames.add(String.valueOf(s.getName()));
                        specialities.add(s);

                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEspecialidad.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<Speciality>> call, Throwable t) {
                            }
        });


    }
}
