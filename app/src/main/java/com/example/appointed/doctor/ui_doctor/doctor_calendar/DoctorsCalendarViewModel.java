package com.example.appointed.doctor.ui_doctor.doctor_calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DoctorsCalendarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DoctorsCalendarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}