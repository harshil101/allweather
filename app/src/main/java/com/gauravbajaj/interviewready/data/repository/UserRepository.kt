package com.gauravbajaj.interviewready.data.repository

import com.gauravbajaj.interviewready.data.api.UserApi
import com.gauravbajaj.interviewready.data.model.User
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    fun getUsers(): Flow<List<User>> = flow {
        try {
            emit(userApi.getUsers())
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
}
