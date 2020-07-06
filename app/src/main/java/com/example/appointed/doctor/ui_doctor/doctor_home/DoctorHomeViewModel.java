package com.example.appointed.doctor.ui_doctor.doctor_home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DoctorHomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DoctorHomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}