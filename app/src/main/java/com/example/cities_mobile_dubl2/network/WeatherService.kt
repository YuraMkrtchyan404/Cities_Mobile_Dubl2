package com.example.cities_mobile_dubl2.network

import com.example.cities_mobile_dubl2.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current.json")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("key") apiKey: String,
        @Query("lang") language: String
    ): WeatherData?
}
