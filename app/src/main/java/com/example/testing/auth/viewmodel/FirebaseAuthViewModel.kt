package com.example.testing.auth.viewmodel

import android.net.Network
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing.auth.model.User
import com.example.testing.auth.repository.FirebaseAuthRepository
import com.example.testing.utils.FirebaseResource
import com.example.testing.utils.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class FirebaseAuthViewModel: ViewModel() {

    private val firebaseAuthRepository = FirebaseAuthRepository()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _registerLiveData: MutableLiveData<FirebaseResource<FirebaseUser>> = MutableLiveData()
    val registerLiveData: LiveData<FirebaseResource<FirebaseUser>> = _registerLiveData

    private val _loginLiveData: MutableLiveData<FirebaseResource<FirebaseUser>> = MutableLiveData()
    val loginLiveData: LiveData<FirebaseResource<FirebaseUser>> = _loginLiveData

    private val _getLiveUserDetails: MutableLiveData<NetworkResponse<User>> = MutableLiveData()
    val getLiveUserDetails: LiveData<NetworkResponse<User>> = _getLiveUserDetails

    private val _saveLiveUserDetails: MutableLiveData<NetworkResponse<Boolean>> = MutableLiveData()
    val saveLiveUserDetails: LiveData<NetworkResponse<Boolean>> = _saveLiveUserDetails

    init {
        if(firebaseAuth.currentUser != null) {
            val currentUser: FirebaseUser = firebaseAuth.currentUser!!
            _loginLiveData.value = FirebaseResource.Success(currentUser)
        }
    }

    fun registerUser(userEmail: String, userPassword: String) = viewModelScope.launch {
        _registerLiveData.value = FirebaseResource.Loading
        val result = firebaseAuthRepository.registerUser(userEmail, userPassword)
        _registerLiveData.value = result
    }

    fun signInUser(userEmail: String, userPasswd: String) = viewModelScope.launch {
        _loginLiveData.value = FirebaseResource.Loading
        val result = firebaseAuthRepository.signInUser(userEmail, userPasswd)
        _loginLiveData.value = result
    }

    fun signOutUser() {
        firebaseAuthRepository.signOutUser()
    }

    fun saveUserDetails(userImage: Uri, userName: String, userEmail: String, userPassword: String) = viewModelScope.launch {
        _saveLiveUserDetails.value = NetworkResponse.Loading()
        val result = firebaseAuthRepository.saveUserDetails(userImage, userName, userEmail, userPassword)
        _saveLiveUserDetails.value = result
    }

    fun getUserDetails() = viewModelScope.launch {
        val result = firebaseAuthRepository.getUserDetails()
        Log.d("TAG", "result" + result)
        _getLiveUserDetails.value = result
    }

}