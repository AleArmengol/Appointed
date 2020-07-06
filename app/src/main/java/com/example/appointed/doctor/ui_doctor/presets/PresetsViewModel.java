package com.example.appointed.doctor.ui_doctor.presets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PresetsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PresetsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}