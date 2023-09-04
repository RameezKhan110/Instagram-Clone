package com.example.testing.auth.repository

import androidx.lifecycle.LiveData
import com.example.testing.auth.room.Auth
import com.example.testing.auth.room.AuthDao
import com.example.testing.auth.room.User
import com.example.testing.auth.room.UserDao
import com.example.testing.home.room.post.story.Story

class AuthRepository(private val authDao: AuthDao, private val userDao: UserDao) {

    suspend fun registerUser(registerUser: Auth) {
        authDao.registerUser(registerUser)
    }

    suspend fun loginUser(userEmail: String, userPassword: String): Auth? {
        return authDao.loginUser(userEmail, userPassword)
    }

    fun isEmailRegistered(userEmail: String): LiveData<Auth> {
        return authDao.isEmailRegistered(userEmail)
    }

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    fun getUserId(userEmail: String): LiveData<Int> {
        return authDao.getUserId(userEmail)
    }

    fun getDataAsPerEmail(userEmail: String): LiveData<List<Auth>> {
        return authDao.getDataAsPerEmail(userEmail)
    }

    fun getStoryAsPerId(userId: Int): LiveData<List<Story>> {
        return authDao.getStoryAsPerId(userId)
    }
}