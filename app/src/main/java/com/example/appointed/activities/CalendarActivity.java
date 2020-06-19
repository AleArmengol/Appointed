package com.example.appointed.activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
//import android.widget.CalendarView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.appointed.R;
import com.example.appointed.endpoints.DateService;
import com.example.appointed.models.DateAppointment;
import com.example.appointed.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    List<Calendar> selectedDates;
    List<Calendar> disabledDates;
    Calendar currdayCalendar;
    Calendar twoMonthsAhead;
    Date currday;
    Retrofit retrofit;
    String specialityName;
    Integer doctorId;
    Integer doctorIdInteger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        specialityName = getIntent().getStringExtra("specialityName");
        doctorId = getIntent().getIntExtra("doctorId", -1);
        if(doctorId == -1){
            doctorIdInteger =  null;
        } else {
            doctorIdInteger = new Integer(doctorId);
        }

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        selectedDates = calendarView.getSelectedDates();
        disabledDates = new ArrayList<Calendar>();

        //SET MINIMUM AND MAXIMUM DATES
        currday = java.util.Calendar.getInstance().getTime();
        currdayCalendar = Calendar.getInstance();
        currdayCalendar.setTime(currday);
        twoMonthsAhead = Calendar.getInstance();
        twoMonthsAhead.setTime(currday);
        twoMonthsAhead.add(Calendar.MONTH, 2);

        retrofit = RetrofitClientInstance.getRetrofitInstance();
        DateService dateService = retrofit.create(DateService.class);
        Call<List<DateAppointment>> call = dateService.getDates(specialityName, doctorIdInteger);


        call.enqueue(new Callback<List<DateAppointment>>() {
            @Override
            public void onResponse(Call<List<DateAppointment>> call, Response<List<DateAppointment>> response) {
                if(android.os.Debug.isDebuggerConnected()){
                    android.os.Debug.waitForDebugger();
                }
                if(response.body() != null){
                    for(DateAppointment date : response.body()){
                        Calendar availableDate = Calendar.getInstance();
                        availableDate.set(date.getYear(), date.getMonth() - 1, date.getDay());
                        selectedDates.add(availableDate);
                    }
                    selectDates(selectedDates);
                }
            }

            @Override
            public void onFailure(Call<List<DateAppointment>> call, Throwable t) {

            }
        });

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                if(android.os.Debug.isDebuggerConnected()){
                    android.os.Debug.waitForDebugger();
                }
                if(!disabledDates.contains(eventDay.getCalendar())) {
                    Calendar clickedDayCalendar = eventDay.getCalendar();
                    int date = clickedDayCalendar.get(Calendar.DATE);
                    int month = clickedDayCalendar.get(Calendar.MONTH) + 1;
                    int year = clickedDayCalendar.get(Calendar.YEAR);
                    Intent outputIntent = new Intent();
                    outputIntent.putExtra("day", date);
                    outputIntent.putExtra("month", month);
                    outputIntent.putExtra("year", year);
                    setResult(Activity.RESULT_OK, outputIntent);
                    finish();
                }
            }
        });

    }


    public void selectDates(List<Calendar> datesToSelect){
        calendarView.setSelectedDates(datesToSelect);
        Calendar backup = (Calendar) currdayCalendar.clone();
        Calendar twoMonthsAheadBackup = (Calendar)twoMonthsAhead.clone();
        twoMonthsAheadBackup.add(Calendar.DATE,1);

        while (currdayCalendar.compareTo(twoMonthsAheadBackup) < 0){
            boolean isSelected = false;
            for(Calendar availableDate : datesToSelect){
                if(availableDate.getTime().toString().substring(0,10).compareTo(currdayCalendar.getTime().toString().substring(0,10)) == 0){
                    isSelected = true;
                    break;
                }
            }
            if(!isSelected){
                disabledDates.add((Calendar)currdayCalendar.clone());
            } else {
            }
            currdayCalendar.add(Calendar.DATE, 1);
        }

        currdayCalendar = (Calendar)backup.clone();
        calendarView.setDisabledDays(disabledDates); //IS NOT WORKING
        calendarView.setMinimumDate(currdayCalendar);
        calendarView.setMaximumDate(twoMonthsAhead);
    }
}
