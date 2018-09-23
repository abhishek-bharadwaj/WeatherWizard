package com.abhishek.weatherwizard.view

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.abhishek.weatherwizard.data.repository.livedata.Resource
import com.abhishek.weatherwizard.data.repository.room.WeatherData
import com.abhishek.weatherwizard.domain.WeatherDataInteractor
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherDataViewModel : ViewModel(), LifecycleObserver {

    private val interactor: WeatherDataInteractor = WeatherDataInteractor()
    private var weatherLiveData = MutableLiveData<Resource<WeatherData>>()

    fun getWeatherLiveData(latitude: Double,
                           longitude: Double): MutableLiveData<Resource<WeatherData>> {
        getWeatherData(latitude, longitude)
        return weatherLiveData
    }

    private fun getWeatherData(latitude: Double, longitude: Double) {
        weatherLiveData.value = Resource.loading(null)
        interactor.getWeatherData(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<LiveData<WeatherData>> {
                override fun onSuccess(t: LiveData<WeatherData>) {
                    weatherLiveData.value = Resource.success(t.value)
                }

                override fun onError(e: Throwable) {
                    weatherLiveData.value = Resource.error(e.message, null)
                }

                override fun onSubscribe(d: Disposable) {

                }
            })
    }
}