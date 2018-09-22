package com.abhishek.weatherwizard.data

import android.location.Location
import com.abhishek.weatherwizard.data.repository.api.Api
import com.abhishek.weatherwizard.data.model.WeatherDataApiResponse
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object WeatherDataRepository {

    fun getWeatherData(callback: DataCallback, location: Location) {
        Api.getWeatherData("${location.latitude},${location.longitude}")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<WeatherDataApiResponse> {
                override fun onSuccess(t: WeatherDataApiResponse) {
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