package com.abhishek.weatherwizard.data.repository.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = WEATHER_DATA_TABLE, primaryKeys = [COLUMN_LAT, COLUMN_LON])
class WeatherData(
    @ColumnInfo(name = COLUMN_LAT)
    var latitude: Double = 0.0,
    @ColumnInfo(name = COLUMN_LON)
    var longitude: Double = 0.0,
    @ColumnInfo(name = COLUMN_NAME)
    var name: String = "",
    @ColumnInfo(name = COLUMN_REGION)
    var region: String = "",
    @ColumnInfo(name = COLUMN_COUNTRY)
    var country: String = "",
    @ColumnInfo(name = COLUMN_CURRENT_TMP)
    var currentTmp: Double = 0.0)