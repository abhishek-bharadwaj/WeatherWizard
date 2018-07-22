package com.abhishek.weatherwizard.data

import android.location.Location
import com.abhishek.weatherwizard.data.api.Api
import com.abhishek.weatherwizard.data.model.WeatherData
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object WeatherDataRepository {

    fun getWeatherData(callback: DataCallback, location: Location) {
        Api.getWeatherData("${location.latitude},${location.longitude}")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<WeatherData> {
                override fun onSuccess(t: WeatherData) {
                    callback.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    callback.onError(e)
                }

                override fun onSubscribe(d: Disposable) {

                }
            })
    }
}