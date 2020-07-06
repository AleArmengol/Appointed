package com.example.appointed.endpoints;

import com.example.appointed.models.Speciality;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpecialityService {
    String API_ROUTE="/specialities";
    @GET(API_ROUTE)
    Call<List<Speciality>> getResponse();

    String API_ROUTE_DOCTOR_SPECIALITIES="/specialities";
    @GET(API_ROUTE_DOCTOR_SPECIALITIES)
    Call<List<Speciality>> getResponse(@Query("doctor_id") int doctor_id);

}
