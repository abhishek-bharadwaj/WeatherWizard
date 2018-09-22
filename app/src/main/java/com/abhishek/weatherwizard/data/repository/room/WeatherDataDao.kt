package com.abhishek.weatherwizard.data.repository.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface WeatherDataDao {

    @Insert
    fun insertWeatherData(weatherData: WeatherData)

    @Query("select * from WEATHER_DATA where lat=:lat AND lon=:lon")
    fun getWeatherData(lat: Double, lon: Double): WeatherData
}