package com.example.testing.auth.repository

import androidx.lifecycle.LiveData
import com.example.testing.auth.room.Auth
import com.example.testing.auth.room.AuthDao

class AuthRepository(private val authDao: AuthDao) {

    suspend fun registerUser(registerUser: Auth) {
        authDao.registerUser(registerUser)
    }

    suspend fun loginUser(userEmail: String, userPassword: String): Auth? {
        return authDao.loginUser(userEmail, userPassword)
    }

    fun isEmailRegistered(userEmail: String): LiveData<Auth> {
        return authDao.isEmailRegistered(userEmail)
    }
}