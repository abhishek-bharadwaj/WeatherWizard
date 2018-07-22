package com.abhishek.weatherwizard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Api.getWeatherData("paris")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<WeatherData> {
                override fun onSuccess(t: WeatherData) {
                    print(t.toString())
                }

                override fun onError(e: Throwable) {
                    print(e.toString())
                }

                override fun onSubscribe(d: Disposable) {

                }
            })
    }

    fun print(text: String) = Log.d("OOOOOOOOOOO", text)
}
