package com.example.appointed.endpoints;

import com.example.appointed.models.WeeklyPreset;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeeklyPresetService {
    String API_ROUTE_WEEKLY_PRESETS = "/weekly_presets";
    @GET(API_ROUTE_WEEKLY_PRESETS)
    Call<List<WeeklyPreset>> getWeeklyPresetsByDoctor(@Query("doctor_id") int doctor_id);
}
