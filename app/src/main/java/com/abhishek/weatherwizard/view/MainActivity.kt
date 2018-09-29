package com.abhishek.weatherwizard.view

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.abhishek.weatherwizard.R
import com.abhishek.weatherwizard.data.repository.livedata.LiveDataStatus
import com.abhishek.weatherwizard.data.repository.room.WeatherData
import com.abhishek.weatherwizard.gone
import com.abhishek.weatherwizard.isNetworkAvailable
import com.abhishek.weatherwizard.visible
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val PERMISSION_REQUEST_LOCATION = 34142
    }

    private var permissionDialog: AlertDialog? = null
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: WeatherDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(WeatherDataViewModel::class.java)
        lifecycle.addObserver(viewModel)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkForPermissionAndRequestData()
        initView()
    }

    private fun initView() {
        forecastAdapter = ForecastAdapter(this)
        rv_forecast.layoutManager = LinearLayoutManager(this)
        rv_forecast.adapter = forecastAdapter
        btn_retry.setOnClickListener(this)
        resetState()
        iv_loading.visible()
    }

    override fun onClick(view: View?) {
        when (view) {
            btn_retry -> checkForPermissionAndRequestData()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestData()
                } else {
                    Toast.makeText(this, "Please give location permission from settings!",
                        Toast.LENGTH_SHORT).show()
                    finish()
                    return
                }
            }
        }
    }

    private fun checkForPermissionAndRequestData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showPermissionRequestDialog()
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_LOCATION)
            }
        } else {
            requestData()
        }
    }

    private fun showPermissionRequestDialog() {
        if (permissionDialog != null) {
            permissionDialog?.show()
            return
        }
        val alertBuilder = AlertDialog.Builder(this).apply {
            setCancelable(false)
            setMessage("Location permission is required to show local weather update.")
        }
        alertBuilder.setPositiveButton(android.R.string.yes) { _, _ ->
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
        }
        permissionDialog = alertBuilder.create()
        permissionDialog?.show()
    }

    @SuppressLint("MissingPermission")
    private fun requestData() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "Please check internet connectivity..", Toast.LENGTH_SHORT).show()
            setUpErrorUI()
            return
        }
        resetState()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                Log.e(com.abhishek.weatherwizard.TAG, location?.latitude?.toString() + " - " + location?.longitude)
                if (location != null) {
                    viewModel.getWeatherLiveData(location.latitude, location.longitude)
                        .observe(this, Observer {
                            when (it?.status) {
                                LiveDataStatus.LOADING -> {
                                    iv_loading.visible()
                                    iv_loading.startAnimation(AnimationUtils.loadAnimation(this,
                                        R.anim.rotate_anim))
                                }

                                LiveDataStatus.SUCCESS -> {
                                    if (it.data != null)
                                        setUpSuccessUI(it.data)
                                    else
                                        setUpErrorUI()
                                }

                                LiveDataStatus.ERROR -> {
                                    setUpErrorUI()
                                }
                            }
                        })
                } else {
                    Toast.makeText(this,
                        "Something went wrong please try again later..",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setUpSuccessUI(data: WeatherData) {
        resetState()
        ll_success_ui.visible()
        tv_current_temp.text = getString(R.string.temp_str, data.currentTmp.toInt())
        tv_location.text = data.name
//        forecastAdapter.updateData(data.forecast.forecastDays.takeLast(4).map {
//            Pair(Util.getDayFromDateString(it.date) ?: it.date, it.day.avgTemp.toInt())
//        })
//        rv_forecast.animate().translationY((0).toFloat()).setDuration(1200L)
//            .setInterpolator(AccelerateDecelerateInterpolator()).start()
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
