package com.example.testing.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testing.home.model.HomeModel
import com.example.testing.home.repository.HomeRepository

class HomeViewModel : ViewModel() {

    private val homeRepository = HomeRepository()

    val _getMutableLiveData: MutableLiveData<List<HomeModel>> = MutableLiveData()
    val getLiveData: LiveData<List<HomeModel>> = _getMutableLiveData


    fun addPost() {
        homeRepository.addPost()
    }

    fun getPost() {
        _getMutableLiveData.value = homeRepository.getPost()
    }

}