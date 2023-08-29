package com.example.testing.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing.auth.repository.AuthRepository
import com.example.testing.auth.room.Auth
import com.example.testing.auth.room.AuthDatabase
import com.example.testing.auth.room.CombinedUserData
import com.example.testing.auth.room.User
import com.example.testing.auth.room.UserDatabase
import com.example.testing.home.room.post.story.Story
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    private val authDao = AuthDatabase.getDatabase().authDao()
    val userDao = UserDatabase.getDatabase().userDao()
    private val authRepository = AuthRepository(authDao, userDao)

    private val _loginMutableUser: MutableLiveData<Auth> = MutableLiveData()
    val loginLiveUser: LiveData<Auth> = _loginMutableUser

    fun registerUser(registerUser: Auth) = viewModelScope.launch {
        authRepository.registerUser(registerUser)
    }

    fun loginUser(userEmail: String, userPassword: String) = viewModelScope.launch {
        _loginMutableUser.value = authRepository.loginUser(userEmail, userPassword)
    }

    fun isEmailRegistered(userEmail: String): LiveData<Auth> {
        return authRepository.isEmailRegistered(userEmail)
    }

    fun addUser(user: User) = viewModelScope.launch {
        authRepository.addUser(user)
    }

    fun getUserId(userEmail: String): LiveData<Int> {
        return authRepository.getUserId(userEmail)
    }

    fun getDataAsPerId(userEmail: String): LiveData<List<Auth>> {
        return authRepository.getDataAsPerId(userEmail)
    }

    fun getStoryAsPerId(userId: Int): LiveData<List<Story>> {
        return authRepository.getStoryAsPerId(userId)
    }
}