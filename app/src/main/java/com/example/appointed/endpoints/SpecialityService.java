package com.example.appointed.endpoints;

import com.example.appointed.models.Speciality;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SpecialityService {
    String API_ROUTE="/specialities";
    @GET(API_ROUTE)
    Call<List<Speciality>> getResponse();

}
