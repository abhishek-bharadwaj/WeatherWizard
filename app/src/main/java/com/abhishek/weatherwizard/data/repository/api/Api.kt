package com.abhishek.weatherwizard.data.repository.api

import com.abhishek.weatherwizard.data.model.WeatherDataApiResponse
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object Api {

    private val apiService by lazy {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder().baseUrl("https://api.apixu.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    fun getWeatherData(city: String) = apiService.getWeatherData(city)

    interface ApiService {
        @GET("forecast.json?key=3b38db11aa2f4b3198151314182107")
        fun getWeatherData(@Query("q") city: String, @Query("days") days: Int = 5): Single<WeatherDataApiResponse>
    }
}