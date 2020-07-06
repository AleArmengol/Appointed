package com.example.appointed.posts;

import com.example.appointed.models.TimeFrame;

import java.util.List;


public class PresetPost {
    private int id;
    private int speciality_id;
    private int doctor_id;
    private String name;
    private List<TimeFrame> time_frames;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpeciality_id() {
        return speciality_id;
    }

    public void setSpeciality_id(int speciality_id) {
        this.speciality_id = speciality_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimeFrame> getTime_frames() {
        return time_frames;
    }

    public void setTime_frames(List<TimeFrame> time_frames) {
        this.time_frames = time_frames;
    }
}
