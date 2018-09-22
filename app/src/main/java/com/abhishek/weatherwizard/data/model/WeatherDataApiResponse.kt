package com.abhishek.weatherwizard.data.model

import com.google.gson.annotations.SerializedName

class WeatherDataApiResponse(@SerializedName("location") val location: Location,
                             @SerializedName("current") val current: Current,
                             @SerializedName("forecast") val forecast: Forecast) {

    class Location(@SerializedName("name") val name: String,
                   @SerializedName("region") val region: String,
                   @SerializedName("country") val country: String,
                   @SerializedName("lat") val latitude: Double,
                   @SerializedName("lon") val longitude: Double)

    class Current(@SerializedName("temp_c") val temp: Double)

    class Forecast(@SerializedName("forecastday") val forecastDays: List<ForecastDay>)

    class ForecastDay(@SerializedName("date") val date: String,
                      @SerializedName("day") val day: Day)

    class Day(@SerializedName("avgtemp_c") val avgTemp: Double)
}