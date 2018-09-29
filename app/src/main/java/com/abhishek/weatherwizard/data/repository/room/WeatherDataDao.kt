package com.abhishek.weatherwizard.data.repository.room

import android.arch.persistence.room.*

@Dao
interface WeatherDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherData: WeatherData): Long

    @Query("select * from WEATHER_DATA where lat=:lat AND lon=:lon")
    fun getWeatherData(lat: Double, lon: Double): WeatherData

    @Query("select * from WEATHER_DATA")
    fun getAllData(): List<WeatherData>

    @Update
    fun updateWeatherData(weatherData: WeatherData)

    @Delete
    fun delete(weatherData: WeatherData)

    @Query("delete from WEATHER_DATA")
    fun deleteAll()
}