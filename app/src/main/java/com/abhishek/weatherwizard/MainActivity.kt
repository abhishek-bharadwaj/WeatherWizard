package com.abhishek.weatherwizard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Api.getWeatherData("")
            .compose(Util.applyIOSchedulers())
            .subscribe(object : SingleObserver<WeatherData> {
                override fun onSuccess(t: WeatherData) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onSubscribe(d: Disposable) {

                }
            })
    }
}
