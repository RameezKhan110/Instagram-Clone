package com.example.testing.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing.home.repository.HomeRepository
import com.example.testing.home.room.post.Home
import com.example.testing.home.room.post.HomeDatabase
import com.example.testing.home.room.post.story.Story
import com.example.testing.home.room.post.story.StoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val postDao = HomeDatabase.getDatabase().homeDao()
    val storyDao = StoryDatabase.getDatabase().storyDao()
    val homeRepository = HomeRepository(postDao, storyDao)

    fun addPost(post: Home) = viewModelScope.launch(Dispatchers.IO) {
        homeRepository.addPost(post)
    }

    fun getPost(): LiveData<List<Home>> {
        Log.d("TAG", "get Post View Model: " + homeRepository.getPost())
        return homeRepository.getPost()
    }

    fun deleteTable() = viewModelScope.launch(Dispatchers.IO) {
        homeRepository.deleteTable()
    }

    fun addStory(story: Story) = viewModelScope.launch {
        homeRepository.addStory(story)
    }

    fun getStory(): LiveData<List<Story>> {
        return homeRepository.getStory()
    }

}