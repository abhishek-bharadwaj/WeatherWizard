package com.abhishek.weatherwizard.data

import com.abhishek.weatherwizard.data.model.WeatherDataApiResponse

interface DataCallback {

    fun onSuccess(data: WeatherDataApiResponse)

    fun onError(e: Throwable)
}