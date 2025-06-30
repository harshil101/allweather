package com.harshil.allweather.data.repository

import android.content.Context
import com.harshil.allweather.data.api.WeatherApi
import com.harshil.allweather.data.model.WeatherApiResp
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for fetching user data.
 *
 * This class provides methods to fetch a list of users or a single user by their ID.
 * It uses a [WeatherApi] to make network requests and [Moshi] for JSON parsing.
 * For development purposes, it currently uses a local JSON file ([R.raw.users]) to provide fake user data.
 *
 * @property weatherApi The API service for user-related network calls.
 * @property context The application context, used to access resources.
 * @property moshi The Moshi instance for JSON serialization and deserialization.
 */
@Singleton
class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    @ApplicationContext
    private val context: Context,
    private val moshi: Moshi
) {
    fun getCurrentWeather(lat: String, lon: String): Flow<WeatherApiResp> = flow {
        try {
            emit(weatherApi.getCurrentWeather(lat, lon, appid = "c5d349237851deff2c3563a25688a8c2"))

        } catch (e: Exception) {
            throw Exception("Failed to fetch current weather", e)
        }
    }.flowOn(Dispatchers.IO)
}
