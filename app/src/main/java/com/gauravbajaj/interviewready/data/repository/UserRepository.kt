package com.gauravbajaj.interviewready.data.repository

import android.content.Context
import com.gauravbajaj.interviewready.data.api.UserApi
import com.gauravbajaj.interviewready.data.model.User
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
            context.resources.openRawResource(com.gauravbajaj.interviewready.R.raw.users)
                .bufferedReader()
                .use { it.readText() }
        return moshi.adapter(Array<User>::class.java).fromJson(jsonString)?.toList() ?: emptyList()
    }
}
