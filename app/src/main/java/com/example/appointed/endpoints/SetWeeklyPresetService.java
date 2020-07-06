package com.example.appointed.endpoints;



import com.example.appointed.models.SetWeeklyPreset;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SetWeeklyPresetService {
    String API_ROUTE="/appointments/";
    @Headers("Content-Type: application/json")
    @POST(API_ROUTE)
    Call<Integer> addWeeklyPresetToDates(@Body SetWeeklyPreset setWeeklyPreset);
}
