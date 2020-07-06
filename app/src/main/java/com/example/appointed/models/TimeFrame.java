package com.example.appointed.models;

public class TimeFrame {
    private int id;
    private int daily_preset_id;
    private String start_time;
    private String end_time;
    private String updated_at;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDaily_preset_id() {
        return daily_preset_id;
    }

    public void setDaily_preset_id(int daily_preset_id) {
        this.daily_preset_id = daily_preset_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
