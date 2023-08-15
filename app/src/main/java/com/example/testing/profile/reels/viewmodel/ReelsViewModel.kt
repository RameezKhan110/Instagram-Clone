package com.example.testing.profile.reels.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testing.profile.reels.model.ReelsModel
import com.example.testing.profile.reels.repository.ReelsRepository

class ReelsViewModel: ViewModel() {

    private val reelsRepository = ReelsRepository()

    private val _getMutableReels: MutableLiveData<List<ReelsModel>> = MutableLiveData()
    val getLiveReels: LiveData<List<ReelsModel>> = _getMutableReels


    fun addReels() {
        reelsRepository.addReels()
    }

    fun getReels() {
        _getMutableReels.value = reelsRepository.getReels()
    }
}