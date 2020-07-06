package com.example.appointed.models;

import java.util.List;

public class SetDailyPreset {

    private List<DateAppointment> dates_to_set;
    private int daily_preset_id;

    public SetDailyPreset(int weekly_preset_id, List<DateAppointment> dates_to_set){
        this.daily_preset_id = weekly_preset_id;
        this.dates_to_set = dates_to_set;
    }

    public List<DateAppointment> getDates_to_set() {
        return dates_to_set;
    }

    public void setDates_to_set(List<DateAppointment> dates_to_set) {
        this.dates_to_set = dates_to_set;
    }

    public int getDaily_preset_id() {
        return daily_preset_id;
    }

    public void setDaily_preset_id(int daily_preset_id) {
        this.daily_preset_id = daily_preset_id;
    }
}
