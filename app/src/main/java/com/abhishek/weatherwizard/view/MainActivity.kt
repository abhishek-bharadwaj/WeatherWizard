package com.abhishek.weatherwizard.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.abhishek.weatherwizard.R
import com.abhishek.weatherwizard.data.DataCallback
import com.abhishek.weatherwizard.data.WeatherDataRepository
import com.abhishek.weatherwizard.data.model.WeatherData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WeatherDataRepository.getWeatherData(this)
    }

    override fun onSuccess(data: WeatherData) {
        tv_current_temp.text = getString(R.string.temp_str, data.current.temp.toInt())
        tv_location.text = data.location.name
    }

    override fun onError(e: Throwable) {

    }
}
