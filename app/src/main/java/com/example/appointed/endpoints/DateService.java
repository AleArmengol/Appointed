package com.example.appointed.endpoints;

import com.example.appointed.models.DateAppointment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DateService {
    String API_ROUTE="/appointment_dates/";

    @GET(API_ROUTE)
    Call<List<DateAppointment>> getDates(@Query("speciality_name") String speciality_name, @Query("doctor_id") Integer doctor_id);
}

