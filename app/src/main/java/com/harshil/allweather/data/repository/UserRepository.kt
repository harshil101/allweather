package com.harshil.allweather.data.repository

import android.content.Context
import com.harshil.allweather.data.api.UserApi
import com.harshil.allweather.data.model.User
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repository for fetching user data.
 *
 * This class provides methods to fetch a list of users or a single user by their ID.
 * It uses a [UserApi] to make network requests and [Moshi] for JSON parsing.
 * For development purposes, it currently uses a local JSON file ([R.raw.users]) to provide fake user data.
 *
 * @property userApi The API service for user-related network calls.
 * @property context The application context, used to access resources.
 * @property moshi The Moshi instance for JSON serialization and deserialization.
 */
@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi,
    @ApplicationContext
    private val context: Context,
    private val moshi: Moshi
) {
    fun getUsers(): Flow<List<User>> = flow {
        try {
//            emit(userApi.getUsers())
            emit(getFakeUsers())

        } catch (e: Exception) {
            throw Exception("Failed to fetch users", e)
        }
    }

    fun getUser(userId: String): Flow<User> = flow {
        try {
            emit(userApi.getUser(userId))
        } catch (e: Exception) {
            throw Exception("Failed to fetch user", e)
        }
    }

    private fun getFakeUsers(): List<User> {
        val jsonString =
            context.resources.openRawResource(com.harshil.allweather.R.raw.users)
                .bufferedReader()
                .use { it.readText() }
        return moshi.adapter(Array<User>::class.java).fromJson(jsonString)?.toList() ?: emptyList()
    }
}
