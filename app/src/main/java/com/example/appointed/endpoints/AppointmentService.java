package com.example.appointed.endpoints;

import com.example.appointed.models.Appointment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppointmentService {
    String API_ROUTE_CANCELLED_APPOINTMENTS = "/appointments";
    @GET(API_ROUTE_CANCELLED_APPOINTMENTS)
    Call<List<Appointment>> getCancelledAppointments(@Query("patient_id") int patient_id, @Query("status") String status);

    String API_ROUTE_PAST_APPOINTMENTS = "/appointments";
    @GET(API_ROUTE_PAST_APPOINTMENTS)
    Call<List<Appointment>> getPastAppointments(@Query("patient_id") int patient_id, @Query("status") String status);


    String API_ROUTE_BOOKED_APPOINTMENTS = "/appointments";
    @GET(API_ROUTE_BOOKED_APPOINTMENTS)
    Call<List<Appointment>> getBookedAppointments(@Query("patient_id") int patient_id, @Query("status") String status);

    String API_ROUTE_SEARCH_APPOINTMENTS = "/appointments/0";
    @GET(API_ROUTE_SEARCH_APPOINTMENTS)
    Call<List<Appointment>> getAppointments(@Query("doctor_id") int doctor_id, @Query("speciality_name") String speciality_name, @Query("day") Integer day, @Query("month") Integer month, @Query("year") Integer year);
}