package com.abhishek.weatherwizard.view

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.abhishek.weatherwizard.TAG
import com.abhishek.weatherwizard.data.Optional
import com.abhishek.weatherwizard.data.repository.livedata.Resource
import com.abhishek.weatherwizard.data.repository.room.WeatherData
import com.abhishek.weatherwizard.domain.WeatherDataInteractor
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@SuppressLint("LogNotTimber")
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
            .subscribe(object : SingleObserver<Optional<WeatherData>> {
                override fun onSuccess(t: Optional<WeatherData>) {
                    if (t.isEmpty()) {
                        return
                    }
                    weatherLiveData.value = Resource.success(t.get())
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, e.message ?: "")
                    weatherLiveData.value = Resource.error(e.message, null)
                }

                override fun onSubscribe(d: Disposable) {

                }
            })
    }
}