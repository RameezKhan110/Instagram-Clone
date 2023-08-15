package com.example.testing.profile.tag_post.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testing.profile.tag_post.model.TagPostModel
import com.example.testing.profile.tag_post.repository.TagPostRepository

class TagPostViewModel(): ViewModel(){
    val tagPostRepository = TagPostRepository()

    private val _getMutableTagPost: MutableLiveData<List<TagPostModel>> = MutableLiveData()
    val getLiveTagPost: LiveData<List<TagPostModel>> = _getMutableTagPost
    fun addTagPost() {
        tagPostRepository.addTagPost()
    }

    fun getTagPost() {
        _getMutableTagPost.value = tagPostRepository.getTagPosts()
    }
}