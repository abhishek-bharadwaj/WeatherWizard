package com.abhishek.weatherwizard

import android.content.Context
import android.net.ConnectivityManager
import android.text.format.DateFormat
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "OOOOOOO"
private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}

fun getDayFromDateString(dateString: String): String? {
    return try {
        DateFormat.format("EEEE", dateFormat.parse(dateString)).toString()
    } catch (e: Exception) {
        null
    }
}

fun roundTo4DecimalPlaces(value: Double): Double {
    val d = BigDecimal(value)
    d.setScale(4, BigDecimal.ROUND_HALF_UP)
    return d.toDouble()
}