package com.example.appointed.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.appointed.R;
import com.example.appointed.endpoints.DailyPresetService;
import com.example.appointed.endpoints.SetDailyPresetService;
import com.example.appointed.endpoints.SetWeeklyPresetService;
import com.example.appointed.endpoints.WeeklyPresetService;
import com.example.appointed.models.DailyPreset;
import com.example.appointed.models.DateAppointment;
import com.example.appointed.models.Doctor;
import com.example.appointed.models.Patient;
import com.example.appointed.models.SetDailyPreset;
import com.example.appointed.models.SetWeeklyPreset;
import com.example.appointed.models.WeeklyPreset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditCalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    List<Calendar> selectedDates;
    List<Calendar> disabledDates;
    Calendar currdayCalendar;
    Calendar twoMonthsAhead;
    Button addWeeklyPresetBtn;
    Button addDailyPresetBtn;
    Button editDayBtn;
    Spinner presetSpinner;
    Button applyPresetBtn;
    List<String> weeklyPresetNames = new ArrayList<>();
    List<String> dailyPresetNames = new ArrayList<>();
    List<WeeklyPreset> weeklyPresets = new ArrayList<>();
    List<DailyPreset> dailyPresets = new ArrayList<>();
    ArrayAdapter<String> weeklyPresetsAdapter;
    ArrayAdapter<String> dailyPresetsAdapter;
    int idDailyPresetSelected;
    int idWeeklyPresetSelected;
    Doctor loggedDoctor;

    MotionEvent me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_calendar);
        calendarView = findViewById(R.id.editCalendarView);
        selectedDates = calendarView.getSelectedDates();
        disabledDates = new ArrayList<Calendar>();
        loggedDoctor = (Doctor) getIntent().getSerializableExtra("loggedDoctor");
        boolean weeklyPresetBtnEnable = false;


        //SET MINIMUM AND MAXIMUM DATES
        java.util.Date currday = java.util.Calendar.getInstance().getTime();
        currdayCalendar = Calendar.getInstance();
        currdayCalendar.setTime(currday);
        twoMonthsAhead = Calendar.getInstance();
        twoMonthsAhead.setTime(currday);
        twoMonthsAhead.add(Calendar.MONTH, 2);

        //custom double tap

        final long[] prevTouch = {0};

        //Buttons
        addDailyPresetBtn = (Button) findViewById(R.id.addDailyPresetBtn);
        addWeeklyPresetBtn = (Button) findViewById(R.id.addWeeklyPresetBtn);
        editDayBtn = (Button) findViewById(R.id.editDayBtn);

        //Spinner

        addDailyPresetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditCalendarActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.add_preset, null);
                TextView selectPresetTxt = (TextView) mView.findViewById(R.id.seleccionarPresetTxt);
                selectPresetTxt.setText("Seleccionar Preset Diario");
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                dailyPresetNames = new ArrayList<>();
                dailyPresetsAdapter = new ArrayAdapter<>(mBuilder.getContext(), android.R.layout.simple_spinner_item, dailyPresetNames);
                dailyPresetNames.add("Seleccione un preset");
                presetSpinner = (Spinner) mView.findViewById(R.id.presetSpinner);
                addDailyPresetOnSpinner();
                applyPresetBtn = (Button) mView.findViewById(R.id.applyPresetBtn);
                applyPresetBtn.setAlpha(.3f);
                applyPresetBtn.setClickable(false);
                applyPresetBtn.setEnabled(false);
                presetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(i != 0){
                            idDailyPresetSelected = dailyPresets.get(i - 1).getId();
                            applyPresetBtn.setEnabled(true);
                            applyPresetBtn.setClickable(true);
                            applyPresetBtn.setAlpha(1f);
                        } else {
                            applyPresetBtn.setEnabled(false);
                            applyPresetBtn.setClickable(false);
                            applyPresetBtn.setAlpha(.3f);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                applyPresetBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:3000/").addConverterFactory(GsonConverterFactory.create()).build();
                        SetDailyPresetService sService = retrofit.create(SetDailyPresetService.class);
                        SetDailyPreset bodyToSend = new SetDailyPreset(idDailyPresetSelected, calendarToDate(calendarView.getSelectedDates()));
                        Call<Integer> call = sService.addDailyPresetToDates(bodyToSend);


                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (android.os.Debug.isDebuggerConnected()) {
                                    android.os.Debug.waitForDebugger();
                                }
                                if(response.body() != null){
                                    Context context = getApplicationContext();
                                    int duration = Toast.LENGTH_SHORT;
                                    String toastText = response.body().toString() + " Appointments Added";
                                    Toast toast = Toast.makeText(context, toastText, duration);
                                    toast.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Context context = getApplicationContext();
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, "ERROR CREATING APPOINTMENTS", duration);
                                toast.show();
                            }
                        });

                    }
                });



            }
        });

        addWeeklyPresetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> daysSelectedPerWeek = countDaysSelectedPerWeek(calendarView.getCurrentPageDate());
                printDate("CURRENT PAGE", calendarView.getCurrentPageDate());
                boolean areWeeksFull = true;
                for(Integer i: daysSelectedPerWeek){
                    if(i != 0 && i != 7){
                        areWeeksFull = false;
                        break;
                    }
                }
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                if(areWeeksFull){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditCalendarActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.add_preset, null);
                    TextView selectPresetTxt = (TextView) mView.findViewById(R.id.seleccionarPresetTxt);
                    selectPresetTxt.setText("Seleccionar Preset Semanal");
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    weeklyPresetNames = new ArrayList<>();
                    weeklyPresetsAdapter = new ArrayAdapter<>(mBuilder.getContext(), android.R.layout.simple_spinner_item, weeklyPresetNames);
                    weeklyPresetNames.add("Seleccione un preset");
                    presetSpinner = (Spinner) mView.findViewById(R.id.presetSpinner);
                    addWeeklyPresetsOnSpinner();
                    applyPresetBtn = (Button) mView.findViewById(R.id.applyPresetBtn);
                    applyPresetBtn.setAlpha(.3f);
                    applyPresetBtn.setClickable(false);
                    applyPresetBtn.setEnabled(false);
                    presetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i != 0){
                                idWeeklyPresetSelected = weeklyPresets.get(i - 1).getId();
                                applyPresetBtn.setEnabled(true);
                                applyPresetBtn.setClickable(true);
                                applyPresetBtn.setAlpha(1f);
                            } else {
                                applyPresetBtn.setEnabled(false);
                                applyPresetBtn.setClickable(false);
                                applyPresetBtn.setAlpha(.3f);
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    applyPresetBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:3000/").addConverterFactory(GsonConverterFactory.create()).build();
                            SetWeeklyPresetService sService = retrofit.create(SetWeeklyPresetService.class);
                            SetWeeklyPreset bodyToSend = new SetWeeklyPreset(idWeeklyPresetSelected, calendarToDate(calendarView.getSelectedDates()));
                            Call<Integer> call = sService.addWeeklyPresetToDates(bodyToSend);


                            call.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (android.os.Debug.isDebuggerConnected()) {
                                        android.os.Debug.waitForDebugger();
                                    }
                                    if(response.body() != null){
                                        String toastText = response.body().toString() + " Appointments Added";
                                        Toast toast = Toast.makeText(context, toastText, duration);
                                        toast.show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast toast = Toast.makeText(context, "ERROR CREATING APPOINTMENTS", duration);
                                    toast.show();
                                }
                            });

                        }
                    });
                } else{
                    //popup with warning
                    Toast toast = Toast.makeText(context, "SAD!", duration);
                    toast.show();
                }
            }
        });

        editDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarView.getSelectedDates().size() != 1){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "You have to select only one day", duration);
                    toast.show();
                } else {
                    //call to edit preset
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "go to edit day activity", duration);
                    toast.show();
                }
            }
        });

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDayClick(EventDay eventDay) {
                if(android.os.Debug.isDebuggerConnected()){
                    android.os.Debug.waitForDebugger();
                }
                Date d = new Date();
                long now = d.getTime();
                if(now < prevTouch[0] + 500){
                    long tbt = prevTouch[0] - now;
                    Log.d("TIMEBETWEENTAPDOUBLETAP", Long.toString(tbt));
                    prevTouch[0] = now;
                    selectWeek(eventDay.getCalendar());

                } else {
                    long tbt = prevTouch[0] - now;
                    Log.d("TIME BETWEEN TAPS", Long.toString(tbt));
                    Calendar clickedDayCalendar = eventDay.getCalendar();
                    int date = clickedDayCalendar.get(Calendar.DATE);
                    int month = clickedDayCalendar.get(Calendar.MONTH) + 1;
                    int year = clickedDayCalendar.get(Calendar.YEAR);
                    String fullDate = Integer.toString(date) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
                    prevTouch[0] = now;
                }
            }
        });


    }

    public void addWeeklyPresetsOnSpinner(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/").addConverterFactory(GsonConverterFactory.create()).build();

        WeeklyPresetService sService =retrofit.create(WeeklyPresetService.class);
        Call<List<WeeklyPreset>> call = sService.getWeeklyPresetsByDoctor(loggedDoctor.getId());

        call.enqueue(new Callback<List<WeeklyPreset>>() {
            @Override
            public void onResponse(Call<List<WeeklyPreset>> call, Response<List<WeeklyPreset>> response) {
                if(android.os.Debug.isDebuggerConnected()){
                    android.os.Debug.waitForDebugger();
                }
                if(response.body()!=null){
                    for(WeeklyPreset s:response.body()){

                        weeklyPresetNames.add(String.valueOf(s.getName()));
                        weeklyPresets.add(s);

                    }
                    weeklyPresetsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    presetSpinner.setAdapter(weeklyPresetsAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<WeeklyPreset>> call, Throwable t) {
            }
        });
    }

    public void addDailyPresetOnSpinner(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/").addConverterFactory(GsonConverterFactory.create()).build();

        DailyPresetService sService = retrofit.create(DailyPresetService.class);
        Call<List<DailyPreset>> call = sService.getDailyPresetsByDoctor(loggedDoctor.getId());

        call.enqueue(new Callback<List<DailyPreset>>() {
            @Override
            public void onResponse(Call<List<DailyPreset>> call, Response<List<DailyPreset>> response) {
                if(android.os.Debug.isDebuggerConnected()){
                    android.os.Debug.waitForDebugger();
                }
                if(response.body()!=null){
                    for(DailyPreset s:response.body()){

                        dailyPresetNames.add(String.valueOf(s.getName()));
                        dailyPresets.add(s);

                    }
                    dailyPresetsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    presetSpinner.setAdapter(dailyPresetsAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<DailyPreset>> call, Throwable t) {
            }
        });
    }

    public  List<DateAppointment> calendarToDate(List<Calendar> datesCalendar){
        List<DateAppointment> datesToReturn = new ArrayList<>();
        for(Calendar c: datesCalendar){
            DateAppointment newDate = new DateAppointment(c.get(Calendar.DATE), c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR), c.get(Calendar.DAY_OF_WEEK) - 1);
            datesToReturn.add(newDate);
        }
        return datesToReturn;
    }

    public List<Integer> countDaysSelectedPerWeek(Calendar dayOfTheWeek){
        Calendar currentDay = getSundayOfWeek(dayOfTheWeek);
        List<Integer> daysSelectedPerWeek = new ArrayList<Integer>();
        while(currentDay.get(Calendar.MONTH) < dayOfTheWeek.get(Calendar.MONTH) + 1) {
            int count = 0;
            for (int i = 0; i < 7; i++) { //we loop and select 7 days ahead
                if(calendarView.getSelectedDates().contains(currentDay)){
                    count++;
                }
                currentDay.set(currentDay.get(Calendar.YEAR), currentDay.get(Calendar.MONTH), currentDay.get(Calendar.DATE) + 1);
            }
            daysSelectedPerWeek.add(count);
        }
        return daysSelectedPerWeek;
    }
    public boolean isAllWeekSelected(Calendar dayOfTheWeek){
        Calendar currentDay = getSundayOfWeek(dayOfTheWeek);
        for (int i = 0; i < 7; i++) { //we loop and select 7 days ahead
            if (dayOfTheWeek.get(Calendar.DATE) != currentDay.get(Calendar.DATE)) {
                if(!calendarView.getSelectedDates().contains(currentDay)){
                    return false;
                }
            }
            currentDay.set(currentDay.get(Calendar.YEAR), currentDay.get(Calendar.MONTH), currentDay.get(Calendar.DATE) + 1);
        }
        return true;
    }

    public Calendar getSundayOfWeek(Calendar dayOfTheWeek){
        Calendar currentDay = (Calendar) dayOfTheWeek.clone();
        int day = currentDay.get(Calendar.DAY_OF_WEEK);
        while(day != Calendar.SUNDAY){ //loop back each day till we find sunday
            int cy = currentDay.get(Calendar.YEAR);
            int cm = currentDay.get(Calendar.MONTH);
            int cd = currentDay.get(Calendar.DATE) - 1;
            currentDay.set(cy, cm, cd);
            day = currentDay.get(Calendar.DAY_OF_WEEK);
        }
        return currentDay;
    }

    public void printDate(String message, Calendar currentDay){
        String fullDate = Integer.toString(currentDay.get(Calendar.DATE)) + "/" + Integer.toString(currentDay.get(Calendar.MONTH)) + "/" + Integer.toString(currentDay.get(Calendar.YEAR));
        Log.d(message, fullDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void selectWeek(Calendar dayOfTheWeek){

        List<Calendar> weekDays = new ArrayList<Calendar>();
        int selectedDatesSize = calendarView.getSelectedDates().size();
        boolean allWeekSelected = isAllWeekSelected(dayOfTheWeek);
        Calendar firstDayOfWeek = getSundayOfWeek(dayOfTheWeek); //we find the firstDayOfTheWeek
        Calendar currentDay = (Calendar) firstDayOfWeek.clone();
        if(!allWeekSelected) {
            for (int i = 0; i < 7; i++) { //we loop and select 7 days ahead
                weekDays.add((Calendar) currentDay.clone());
                currentDay.set(currentDay.get(Calendar.YEAR), currentDay.get(Calendar.MONTH), currentDay.get(Calendar.DATE) + 1);
            }

            List<Calendar> selectedDates = calendarView.getSelectedDates();
            weekDays.addAll(calendarView.getSelectedDates());
            Set setDays = new HashSet(weekDays);
            List<Calendar> daysToSelect = new ArrayList<Calendar>(setDays);
            daysToSelect.add(dayOfTheWeek);
            calendarView.setSelectedDates(daysToSelect);
        } else{
            deselectWeek(dayOfTheWeek);
        }


    }

    public void deselectWeek(Calendar dayOfTheWeek){
        List<Calendar> selectedDates = calendarView.getSelectedDates();
        Calendar currentDay = getSundayOfWeek(dayOfTheWeek);
        for(int i = 0; i < 7; i++){
            selectedDates.remove(currentDay);
            currentDay.set(currentDay.get(Calendar.YEAR), currentDay.get(Calendar.MONTH), currentDay.get(Calendar.DATE) + 1);
        }
        selectedDates.add(dayOfTheWeek);
        calendarView.setSelectedDates(selectedDates);
    }

}
