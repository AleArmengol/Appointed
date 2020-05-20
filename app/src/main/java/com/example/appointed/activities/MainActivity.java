package com.example.appointed.activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appointed.R;
import com.example.appointed.endpoints.DoctorService;
import com.example.appointed.endpoints.PatientService;
import com.example.appointed.models.Doctor;
import com.example.appointed.models.Patient;
import com.example.appointed.network.RetrofitClientInstance;
import com.example.appointed.ui.login.LoginActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnLogOut;
    TextView txtHello;
    Retrofit retrofit;
    Doctor currentDoctor;
    DoctorService doctorService;
    PatientService patientService;
    Patient currentPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtHello = (TextView) findViewById(R.id.txtHello);
        btnLogOut = (Button) findViewById(R.id.btnHello);
        final String email = getIntent().getStringExtra("userName");
        txtHello.setText(email);
        btnLogOut.setText("Log Out");

        retrofit = RetrofitClientInstance.getRetrofitInstance();
        doctorService = retrofit.create(DoctorService.class);
        Call<Doctor> callDoctor = doctorService.getDoctor(0, email);

        callDoctor.enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> responseDoctor) {
                if(android.os.Debug.isDebuggerConnected()){
                    android.os.Debug.waitForDebugger();
                }
                if(responseDoctor.body() != null){
                    currentDoctor = responseDoctor.body();
                    txtHello.setText("ID " + Integer.toString(currentDoctor.getId()) + "EMAIL: " + currentDoctor.getEmail() + "  NAME: " + currentDoctor.getName() + " " + currentDoctor.getLast_name());
                } else { //if we didn't find the doctor, we will search for the patient

                    txtHello.setText("No info in response.body");
                    patientService = retrofit.create(PatientService.class);
                    Call<Patient> callPatient = patientService.getPatient(0, email);

                    callPatient.enqueue(new Callback<Patient>() {
                        @Override
                        public void onResponse(Call<Patient> call, Response<Patient> responsePatient) {
                            currentPatient = responsePatient.body();
                            txtHello.setText("ID " + Integer.toString(currentPatient.getId()) + "EMAIL: " + currentPatient.getEmail() + "  NAME: " + currentPatient.getName() + " " + currentPatient.getLast_name());
                        }
                        @Override
                        public void onFailure(Call<Patient> call, Throwable t) {
                            txtHello.setText(t.getMessage());
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                txtHello.setText(t.getMessage());

            }
        });



        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logInIntent = new Intent(MainActivity.this, LoginActivity.class);
                logInIntent.putExtra("isLoggingOut", "y");
                logInIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logInIntent);
                finish();
            }
        });
    }


}