package com.example.testing.profile.timeline_post.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testing.profile.timeline_post.model.PostModel
import com.example.testing.profile.timeline_post.repository.PostRepository

class PostViewModel: ViewModel() {

    private val postRepository = PostRepository()

    private val _getMutableTimelinePost: MutableLiveData<List<PostModel>> = MutableLiveData()
    val getLiveTimelinePost: LiveData<List<PostModel>> = _getMutableTimelinePost


    fun addTimelinePost(){
        postRepository.addTimelinePost()
    }

    fun getTimelinePost() {
        _getMutableTimelinePost.value = postRepository.getTimeLinePost()
    }
}