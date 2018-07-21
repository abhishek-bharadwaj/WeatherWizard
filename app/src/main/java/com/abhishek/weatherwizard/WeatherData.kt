package com.abhishek.weatherwizard

import com.google.gson.annotations.SerializedName

class WeatherData(@SerializedName("location") val location: Location,
                  @SerializedName("current") val current: Current,
                  @SerializedName("forecast") val forecast: Forecast) {

    class Location(@SerializedName("name") val name: String)

    class Current(@SerializedName("temp_c") val temp: Double)

    class Forecast(@SerializedName("forecastday") val forecastDays: List<ForecastDay>)

    class ForecastDay(@SerializedName("date") val date: String,
                      @SerializedName("day") val day: Day)

    class Day(@SerializedName("avgtemp_c") val avgTemp: Double)
}