package com.example.appointed.endpoints;

import com.example.appointed.models.DailyPreset;
import com.example.appointed.posts.PresetPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DailyPresetService {

    String API_ROUTE_DAILY_PRESETS = "/daily_presets";
    @GET(API_ROUTE_DAILY_PRESETS)
    Call<List<DailyPreset>> getPresets(@Query("doctor_id") int doctor_id);

    String API_ROUTE_DELETE_PRESET = "/daily_presets/{id}";
    @DELETE(API_ROUTE_DELETE_PRESET)
    Call<DailyPreset> deletePreset(@Path("id") int id);

    String API_ROUTE_CREATE_PRESET = "/daily_presets";
    @POST(API_ROUTE_CREATE_PRESET)
    Call<PresetPost> createPreset(@Body PresetPost post);

}
