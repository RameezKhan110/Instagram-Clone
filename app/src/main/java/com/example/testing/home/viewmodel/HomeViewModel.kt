package com.example.testing.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing.api.PostApiService
import com.example.testing.home.repository.HomeRepository
import com.example.testing.home.room.post.story.Story
import com.example.testing.home.room.post.story.StoryDatabase
import com.example.testing.model.Urls
import com.example.testing.model.Wallpapers
import com.example.testing.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val postApiService: PostApiService = PostApiService
    val storyDao = StoryDatabase.getDatabase().storyDao()
    val homeRepository = HomeRepository(postApiService, storyDao)

    private val mutablePostList: MutableLiveData<Resource<List<Wallpapers>>> = MutableLiveData()
    val livePostLists: LiveData<Resource<List<Wallpapers>>>
        get() = mutablePostList

//    init {
//        getApiPosts()
//    }

    fun getApiPosts() = viewModelScope.launch {
        Log.d("TAG", "api called")
        mutablePostList.postValue(Resource.Loading(null))
        try {
            val result = homeRepository.getApiPosts()
            mutablePostList.postValue(Resource.Success(result))
        } catch (t: Throwable) {
            mutablePostList.postValue(t.localizedMessage?.let {
                Resource.Error(it, null)
            })
        }
    }
//    val postDao = HomeDatabase.getDatabase().homeDao()
//    val storyDao = StoryDatabase.getDatabase().storyDao()
//    val homeRepository = HomeRepository(postDao, storyDao)

//    fun addPost(post: Home) = viewModelScope.launch(Dispatchers.IO) {
//        homeRepository.addPost(post)
//    }
//
//    fun getPost(): LiveData<List<Home>> {
//        Log.d("TAG", "get Post View Model: " + homeRepository.getPost())
//        return homeRepository.getPost()
//    }
//
//    fun deleteTable() = viewModelScope.launch(Dispatchers.IO) {
//        homeRepository.deleteTable()
//    }

    fun addStory(story: Story) = viewModelScope.launch {
        homeRepository.addStory(story)
    }

    fun getStory(): LiveData<List<Story>> {
        return homeRepository.getStory()
    }

}