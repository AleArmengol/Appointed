package com.example.appointed.endpoints;

import com.example.appointed.models.Doctor;
import com.example.appointed.models.Speciality;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DoctorService {
    String API_ROUTE="/doctors/0";
    @GET(API_ROUTE)
    Call<Doctor> getDoctor(@Query("email") String email);



    String API_ROUTE_Doctor_By_Speciality="/doctors";
    @GET(API_ROUTE_Doctor_By_Speciality)
    Call<List<Doctor>> getDoctorsBySpeciality(@Query("id") int id, @Query("day") Integer day, @Query("month") Integer month, @Query("year") Integer year);
}

