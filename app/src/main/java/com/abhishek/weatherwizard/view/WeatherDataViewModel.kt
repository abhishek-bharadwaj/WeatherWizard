package com.abhishek.weatherwizard.view

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.abhishek.weatherwizard.data.repository.livedata.LiveDataStatus
import com.abhishek.weatherwizard.data.repository.livedata.Resource
import com.abhishek.weatherwizard.data.repository.room.WeatherData

class WeatherDataViewModel : ViewModel(), LifecycleObserver {

    private val weatherLiveData = MutableLiveData<Resource<WeatherData>>()

    fun getWeatherLiveData(latitude: Double, longitude: Double): MutableLiveData<Resource<WeatherData>> {

    }
}