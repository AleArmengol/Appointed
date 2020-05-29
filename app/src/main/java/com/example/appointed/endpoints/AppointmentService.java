package com.example.appointed.endpoints;

import com.example.appointed.models.Appointment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppointmentService {
    String API_ROUTE = "/appointments?patient_id=5&status=cancelled";
    @GET(API_ROUTE)
    Call<List<Appointment>> getAppointments();
}