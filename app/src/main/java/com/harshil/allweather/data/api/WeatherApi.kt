package com.harshil.allweather.data.api

import com.harshil.allweather.data.model.WeatherApiResp
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface defining the API endpoints for user-related operations.
 * This interface is used by Retrofit to generate the network request implementations.
 */
interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("APPID") appid: String): WeatherApiResp

}
