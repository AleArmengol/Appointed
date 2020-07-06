package com.example.appointed.models;

import java.util.List;

public class SetWeeklyPreset {
    private List<DateAppointment> dates_to_set;
    private int weekly_preset_id;

    public SetWeeklyPreset(int weekly_preset_id, List<DateAppointment> dates_to_set){
        this.weekly_preset_id = weekly_preset_id;
        this.dates_to_set = dates_to_set;
    }

    public List<DateAppointment> getDates_to_set() {
        return dates_to_set;
    }

    public void setDates_to_set(List<DateAppointment> dates_to_set) {
        this.dates_to_set = dates_to_set;
    }

    public int getWeekly_preset_id() {
        return weekly_preset_id;
    }

    public void setWeekly_preset_id(int weekly_preset_id) {
        this.weekly_preset_id = weekly_preset_id;
    }
}
