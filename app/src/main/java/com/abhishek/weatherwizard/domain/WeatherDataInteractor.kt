package com.abhishek.weatherwizard.domain

import android.util.Log
import com.abhishek.weatherwizard.TAG
import com.abhishek.weatherwizard.data.Optional
import com.abhishek.weatherwizard.data.model.WeatherDataApiResponse
import com.abhishek.weatherwizard.data.repository.api.Api
import com.abhishek.weatherwizard.data.repository.room.WeatherData
import com.abhishek.weatherwizard.data.repository.room.WeatherDatabase
import com.abhishek.weatherwizard.roundTo4DecimalPlaces
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherDataInteractor {

    private val weatherDatabase = WeatherDatabase.getInstance()

    fun getWeatherData(latitude: Double, longitude: Double): Single<Optional<WeatherData>> {
        getLatestWeatherDataFromApi(latitude, longitude)
        return Single.defer {
            val optional = Optional(weatherDatabase.weatherDataDao().getWeatherData(
                roundTo4DecimalPlaces(latitude), roundTo4DecimalPlaces(longitude)))
            Single.just(optional)
        }
    }

    private fun getLatestWeatherDataFromApi(latitude: Double, longitude: Double) {
        Api.getWeatherData(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<retrofit2.Response<WeatherDataApiResponse>> {
                override fun onSuccess(t: retrofit2.Response<WeatherDataApiResponse>) {
                    if (!t.isSuccessful) {
                        Log.e(TAG, "Api call failed.")
                        return
                    }
                    val apiResponse = t.body() ?: kotlin.run {
                        Log.e(TAG, "Body is null")
                        return
                    }
                    Log.d(TAG, apiResponse.toString())
                    val data = WeatherData(
                        latitude = roundTo4DecimalPlaces(apiResponse.location.latitude),
                        longitude = roundTo4DecimalPlaces(apiResponse.location.longitude),
                        name = apiResponse.location.name,
                        region = apiResponse.location.region,
                        country = apiResponse.location.country,
                        currentTmp = apiResponse.current.temp)
                    val result =
                        weatherDatabase.weatherDataDao().insertWeatherData(weatherData = data)
                    Log.d(TAG, result.toString())
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.toString())
                }

                override fun onSubscribe(d: Disposable) {

                }
            })
    }
}