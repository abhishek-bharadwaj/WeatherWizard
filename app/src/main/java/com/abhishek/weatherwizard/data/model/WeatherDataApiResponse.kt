package com.abhishek.weatherwizard.data.model

import com.google.gson.annotations.SerializedName

data class WeatherDataApiResponse(@SerializedName("location") val location: Location,
                                  @SerializedName("current") val current: Current,
                                  @SerializedName("forecast") val forecast: Forecast) {

    data class Location(@SerializedName("name") val name: String,
                        @SerializedName("region") val region: String,
                        @SerializedName("country") val country: String,
                        @SerializedName("lat") val latitude: Double,
                        @SerializedName("lon") val longitude: Double)

    data class Current(@SerializedName("temp_c") val temp: Double)

    data class Forecast(@SerializedName("forecastday") val forecastDays: List<ForecastDay>)

    data class ForecastDay(@SerializedName("date") val date: String,
                           @SerializedName("day") val day: Day)

    data class Day(@SerializedName("avgtemp_c") val avgTemp: Double)
}