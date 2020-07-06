package com.example.appointed.endpoints;



import com.example.appointed.models.SetDailyPreset;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SetDailyPresetService {
    String API_ROUTE="/appointments/set_daily_preset_to_dates";
    @Headers("Content-Type: application/json")
    @POST(API_ROUTE)
    Call<Integer> addDailyPresetToDates(@Body SetDailyPreset setDailyPresets);
}
