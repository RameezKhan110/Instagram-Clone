package com.example.testing.home.firestore.repository.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing.home.firestore.repository.FireStoreHomeRepository
import com.example.testing.home.firestore.repository.model.FireStoreStory
import com.example.testing.home.firestore.repository.model.Post
import com.example.testing.utils.NetworkResponse
import kotlinx.coroutines.launch

class FireStoreHomeViewModel : ViewModel() {

    private val fireStoreHomeRepository = FireStoreHomeRepository()

    private val _createLivePost: MutableLiveData<NetworkResponse<Boolean>> = MutableLiveData()
    val createLivePost: LiveData<NetworkResponse<Boolean>> = _createLivePost

    private val _createLiveStory: MutableLiveData<NetworkResponse<Boolean>> = MutableLiveData()
    val createLiveStory: LiveData<NetworkResponse<Boolean>> = _createLiveStory

    private val _getLivePosts: MutableLiveData<NetworkResponse<List<Post>>> = MutableLiveData()
    val getLivePost: LiveData<NetworkResponse<List<Post>>> = _getLivePosts

    private val _getLiveStory: MutableLiveData<List<FireStoreStory>> = MutableLiveData()
    val getLiveStory: LiveData<List<FireStoreStory>> = _getLiveStory

    fun createPost(userPostImageView: Uri) = viewModelScope.launch {
        _createLivePost.value = NetworkResponse.Loading()
        val result = fireStoreHomeRepository.createPost(userPostImageView)
        _createLivePost.value = result
    }

    fun createStory(userStoryImageView: Uri) = viewModelScope.launch {
        _createLiveStory.value = NetworkResponse.Loading()
        val result = fireStoreHomeRepository.createStory(userStoryImageView)
        _createLiveStory.value = result
    }

    fun getPosts() = viewModelScope.launch {
        _getLivePosts.value = NetworkResponse.Loading()
        val result = fireStoreHomeRepository.getPosts()
        _getLivePosts.value = result
    }

    fun getStory() = viewModelScope.launch {
        val result = fireStoreHomeRepository.getStories()
        Log.d("TAG", "calling from Story firestore view model" + result)
        _getLiveStory.value = result
    }
}
