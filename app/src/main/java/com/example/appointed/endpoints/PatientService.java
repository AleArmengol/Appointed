package com.example.appointed.endpoints;

import com.example.appointed.models.Doctor;
import com.example.appointed.models.Patient;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PatientService {
    String API_ROUTE="/patients/0";

    @GET(API_ROUTE)
    Call<Patient> getPatient(@Query("id") int id, @Query("email") String email);
}
