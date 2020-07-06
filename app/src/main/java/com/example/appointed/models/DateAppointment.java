package com.example.appointed.models;

public class DateAppointment {
    private int day;
    private int month;
    private int year;
    private String name;

    public DateAppointment(int day, int month, int year, int name){
        String[] days = new String[] { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
        this.day = day;
        this.month = month;
        this.year = year;
        this.name = days[name];
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public String toString(){
        return name + " " + day + "/" + month + "/" + year;
    }
}
