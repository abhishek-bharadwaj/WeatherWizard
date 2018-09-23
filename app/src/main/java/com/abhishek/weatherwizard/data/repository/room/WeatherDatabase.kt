package com.abhishek.weatherwizard.data.repository.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.abhishek.weatherwizard.MyApplication

@Database(entities = [WeatherData::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDataDao(): WeatherDataDao

    private object HOLDER {

        val INSTANCE =
            Room.databaseBuilder(MyApplication.mContext, WeatherDatabase::class.java, "weather.db")
                .build()

    }

    companion object {
        private val INSTANCE: WeatherDatabase by lazy { HOLDER.INSTANCE }

        @Synchronized
        fun getInstance(): WeatherDatabase = INSTANCE

        fun clearWeatherData() {
            val weatherDatabase = getInstance()
            weatherDatabase.weatherDataDao().deleteAll()
        }
    }
}