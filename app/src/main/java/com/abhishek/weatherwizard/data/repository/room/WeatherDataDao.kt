package com.abhishek.weatherwizard.data.repository.room

import android.arch.persistence.room.*

@Dao
interface WeatherDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherData: WeatherData): Long

    @Query("select * from WEATHER_DATA limit 1")
    fun getWeatherData(): WeatherData

    @Update
    fun updateWeatherData(weatherData: WeatherData)

    @Delete
    fun delete(weatherData: WeatherData)

    @Query("delete from WEATHER_DATA")
    fun deleteAll()
}