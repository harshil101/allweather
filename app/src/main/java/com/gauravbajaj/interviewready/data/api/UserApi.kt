package com.gauravbajaj.interviewready.data.api

import com.gauravbajaj.interviewready.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): User

    @GET("users")
    suspend fun getUsers(): List<User>
}
