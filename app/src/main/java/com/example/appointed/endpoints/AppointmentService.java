package com.example.appointed.endpoints;

import com.example.appointed.models.Appointment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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



    String API_ROUTE_CONFIRMED_APPOINTMENT = "/appointments/{id}";
    @PUT(API_ROUTE_CONFIRMED_APPOINTMENT)
    Call<Appointment> updateConfirmedAppointment(@Path("id") int idAppointment, @Query("patient_name") String patient_name, @Query("patient_id") int patient_id);


    String API_ROUTE_CANCEL_APPOINTMENTS = "/appointments/{id}";
    @PUT(API_ROUTE_CANCEL_APPOINTMENTS)
    Call<Appointment> updateStatus(@Path("id")int id);

    String API_ROUTE_DOCTORS_APPOINTMENTS = "/appointments/doctors_appointments_by_day";
    @GET(API_ROUTE_DOCTORS_APPOINTMENTS)
    Call<List<Appointment>> getDoctorsAppointments(@Query("doctor_id") int doctor_id, @Query("year") int year, @Query("month") int month, @Query("day") int day);

    String API_ROUTE_DOCTORS_CANCELLED_APPOINTMENTS = "/appointments/cancelled_appointments_by_doctor";
    @GET(API_ROUTE_DOCTORS_CANCELLED_APPOINTMENTS)
    Call<List<Appointment>> getCancelledAppointments(@Query("doctor_id") int doctor_id);

}