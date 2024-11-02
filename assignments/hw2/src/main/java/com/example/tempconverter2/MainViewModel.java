package com.example.tempconverter2;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The view model that holds observable value. In this instance, the text controls will update every
 * time {@link #temperatureData} changes its value.
 */
public class MainViewModel extends ViewModel {
    @NonNull
    public final MutableLiveData<Temperature> temperatureData =
        new MutableLiveData<>(new Temperature(0, Temperature.Unit.CELSIUS));
}
