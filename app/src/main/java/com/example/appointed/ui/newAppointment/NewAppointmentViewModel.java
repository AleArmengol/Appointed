package com.example.appointed.ui.newAppointment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewAppointmentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewAppointmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}