package com.example.testing.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing.auth.repository.AuthRepository
import com.example.testing.auth.room.Auth
import com.example.testing.auth.room.AuthDatabase
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    private val authDao = AuthDatabase.getDatabase().authDao()
    private val authRepository = AuthRepository(authDao)

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
}