package com.abhishek.weatherwizard.data.repository

import android.arch.lifecycle.LiveData
import com.abhishek.weatherwizard.data.model.WeatherDataApiResponse
import com.abhishek.weatherwizard.data.repository.livedata.Resource
import com.abhishek.weatherwizard.data.repository.room.WeatherData
import com.abhishek.weatherwizard.data.repository.room.WeatherDatabase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherDataDBHelper {

    private val weatherDatabase = WeatherDatabase.getInstance()

    fun saveWeatherDataToDB(apiResponse: WeatherDataApiResponse) {
        Completable.defer {
            val data = WeatherData(
                latitude = apiResponse.location.latitude,
                longitude = apiResponse.location.longitude,
                name = apiResponse.location.name,
                region = apiResponse.location.region,
                country = apiResponse.location.country,
                currentTmp = apiResponse.current.temp)
            weatherDatabase.weatherDataDao().insertWeatherData(weatherData = data)
            Completable.complete()
        }
    }

    fun getWeatherData(latitude: Double, longitude: Double): LiveData<Resource<WeatherData>> {
        Single.defer {
            Single.just(weatherDatabase.weatherDataDao().getWeatherData(latitude, latitude))
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<LiveData<WeatherData>> {
                override fun onSuccess(t: LiveData<WeatherData>) {

                }

                override fun onError(e: Throwable) {

                }
            })
    }
}