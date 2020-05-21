package com.example.appointed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppointmentService {
    String API_ROUTE = "/appointments/show?patient_id=1&status=past";
    @GET(API_ROUTE)
    Call<List<Appointment>> getAppointments();
}