package com.abhishek.weatherwizard.data.repository.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = WEATHER_DATA_TABLE,
    primaryKeys = [COLUMN_LAT, COLUMN_LON])
class WeatherData(
    @ColumnInfo(name = COLUMN_LAT)
    val latitude: Double = 0.0,
    @ColumnInfo(name = COLUMN_LON)
    val longitude: Double = 0.0,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String = "",
    @ColumnInfo(name = COLUMN_REGION)
    val region: String = "",
    @ColumnInfo(name = COLUMN_COUNTRY)
    val country: String = "",
    @ColumnInfo(name = COLUMN_CURRENT_TMP)
    val currentTmp: Double = 0.0)