package com.example.appointed.endpoints;

import com.example.appointed.models.Appointment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppointmentService {
    String API_ROUTE_CANCELLED_APPOINTMENTS = "/appointments";
    @GET(API_ROUTE_CANCELLED_APPOINTMENTS)
    Call<List<Appointment>> getAppointments(@Query("patient_id") int patient_id, @Query("status") String status);
}