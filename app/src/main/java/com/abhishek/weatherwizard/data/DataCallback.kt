package com.abhishek.weatherwizard.data

import com.abhishek.weatherwizard.data.model.WeatherData

interface DataCallback {

    fun onSuccess(data: WeatherData)

    fun onError(e: Throwable)
}