package com.example.testing.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testing.search.model.SearchModel
import com.example.testing.search.repository.SearchRepository

class SearchViewModel: ViewModel() {

    private val searchRepository = SearchRepository()

    private val _getMutableRandomPosts: MutableLiveData<List<SearchModel>> = MutableLiveData()
    val getLiveRandomPosts: LiveData<List<SearchModel>> = _getMutableRandomPosts

    fun addRandomPosts() {
        searchRepository.addRandomPosts()
    }

    fun getRandomPosts() {
        _getMutableRandomPosts.value = searchRepository.getRandomPosts()
    }
}