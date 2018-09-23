package com.abhishek.weatherwizard.domain

import android.arch.lifecycle.LiveData
import android.util.Log
import com.abhishek.weatherwizard.data.model.WeatherDataApiResponse
import com.abhishek.weatherwizard.data.repository.api.Api
import com.abhishek.weatherwizard.data.repository.room.WeatherData
import com.abhishek.weatherwizard.data.repository.room.WeatherDatabase
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherDataInteractor {

    private val weatherDatabase = WeatherDatabase.getInstance()

    fun getWeatherData(latitude: Double, longitude: Double): Single<LiveData<WeatherData>> {
        getLatestWeatherDataFromApi(latitude, longitude)
        return Single.just(weatherDatabase.weatherDataDao().getWeatherData(latitude, longitude))
    }

    private fun getLatestWeatherDataFromApi(latitude: Double, longitude: Double) {
        Api.getWeatherData(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<retrofit2.Response<WeatherDataApiResponse>> {
                override fun onSuccess(t: retrofit2.Response<WeatherDataApiResponse>) {
                    if (!t.isSuccessful) {
                        Log.e(this.javaClass.simpleName, "Api call failed.")
                        return
                    }
                    val apiResponse = t.body() ?: kotlin.run {
                        Log.e(this.javaClass.simpleName, "Body is null")
                        return
                    }
                    val data = WeatherData(
                        latitude = apiResponse.location.latitude,
                        longitude = apiResponse.location.longitude,
                        name = apiResponse.location.name,
                        region = apiResponse.location.region,
                        country = apiResponse.location.country,
                        currentTmp = apiResponse.current.temp)
                    weatherDatabase.weatherDataDao().insertWeatherData(weatherData = data)
                }

                override fun onError(e: Throwable) {
                    Log.e(this.javaClass.simpleName, e.toString())
                }

                override fun onSubscribe(d: Disposable) {

                }
            })
    }
}