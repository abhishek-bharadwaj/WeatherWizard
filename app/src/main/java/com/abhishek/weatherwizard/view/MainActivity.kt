package com.abhishek.weatherwizard.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.abhishek.weatherwizard.R
import com.abhishek.weatherwizard.Util
import com.abhishek.weatherwizard.data.DataCallback
import com.abhishek.weatherwizard.data.WeatherDataRepository
import com.abhishek.weatherwizard.data.model.WeatherData
import com.abhishek.weatherwizard.gone
import com.abhishek.weatherwizard.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataCallback, View.OnClickListener {

    private lateinit var forecastAdapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastAdapter = ForecastAdapter(this)
        rv_forecast.layoutManager = LinearLayoutManager(this)
        rv_forecast.adapter = forecastAdapter
        btn_retry.setOnClickListener(this)

        requestData()
    }

    override fun onSuccess(data: WeatherData) {
        setUpSuccessUI(data)
    }

    override fun onError(e: Throwable) {
        setUpErrorUI()
    }

    override fun onClick(view: View?) {
        when (view) {
            btn_retry -> requestData()
        }
    }

    private fun requestData() {
        if (!Util.isNetworkAvailable(this)) {
            Toast.makeText(this, "Please check internet connectivity..", Toast.LENGTH_SHORT).show()
            setUpErrorUI()
            return
        }
        resetState()
        iv_loading.visible()
        iv_loading.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_anim))
        WeatherDataRepository.getWeatherData(this)
    }

    private fun setUpSuccessUI(data: WeatherData) {
        resetState()
        ll_success_ui.visible()
        tv_current_temp.text = getString(R.string.temp_str, data.current.temp.toInt())
        tv_location.text = data.location.name
        forecastAdapter.updateData(data.forecast.forecastDays.map {
            Pair(it.date, it.day.avgTemp.toInt())
        })
        rv_forecast.animate().translationY((0).toFloat()).setDuration(1200L)
            .setInterpolator(AccelerateDecelerateInterpolator()).start()
    }

    private fun setUpErrorUI() {
        resetState()
        ll_failure_ui.visible()
    }

    private fun resetState() {
        ll_failure_ui.gone()
        ll_success_ui.gone()
        iv_loading.gone()
        iv_loading.clearAnimation()
    }
}
