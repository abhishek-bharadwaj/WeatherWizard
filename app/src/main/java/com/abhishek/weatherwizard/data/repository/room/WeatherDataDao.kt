package com.abhishek.weatherwizard.data.repository.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface WeatherDataDao {

    @Insert
    fun insertWeatherData(weatherData: WeatherData)

    @Query("select * from WEATHER_DATA where lat=:lat AND lon=:lon")
    fun getWeatherData(lat: Double, lon: Double): LiveData<WeatherData>

    @Update
    fun updateWeatherData(weatherData: WeatherData)

    @Delete
    fun delete(weatherData: WeatherData)

    @Query("delete from WEATHER_DATA")
    fun deleteAll()
}