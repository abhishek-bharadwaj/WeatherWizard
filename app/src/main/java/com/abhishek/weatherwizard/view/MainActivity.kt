package com.abhishek.weatherwizard.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.abhishek.weatherwizard.R
import com.abhishek.weatherwizard.data.DataCallback
import com.abhishek.weatherwizard.data.WeatherDataRepository
import com.abhishek.weatherwizard.data.model.WeatherData
import com.abhishek.weatherwizard.gone
import com.abhishek.weatherwizard.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataCallback {

    private lateinit var forecastAdapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastAdapter = ForecastAdapter(this)
        rv_forecast.layoutManager = LinearLayoutManager(this)
        rv_forecast.adapter = forecastAdapter

        requestData()
    }

    override fun onSuccess(data: WeatherData) {
        setUpSuccessUI(data)
    }

    override fun onError(e: Throwable) {
        setUpErrorUI()
    }

    private fun requestData() {
        iv_loading.visible()
        ll_success_ui.gone()
        ll_failure_ui.gone()
        WeatherDataRepository.getWeatherData(this)
    }

    private fun setUpSuccessUI(data: WeatherData) {
        ll_success_ui.visible()
        ll_failure_ui.gone()
        iv_loading.gone()
        tv_current_temp.text = getString(R.string.temp_str, data.current.temp.toInt())
        tv_location.text = data.location.name
        forecastAdapter.updateData(data.forecast.forecastDays.map {
            Pair(it.date, it.day.avgTemp.toInt())
        })
    }

    private fun setUpErrorUI() {
        ll_failure_ui.visible()
        ll_success_ui.gone()
        iv_loading.gone()
    }
}
