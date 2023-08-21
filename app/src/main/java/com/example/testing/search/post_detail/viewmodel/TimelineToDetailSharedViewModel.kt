package com.example.testing.search.post_detail.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testing.profile.timeline_post.model.PostModels

class TimelineToDetailSharedViewModel: ViewModel() {

    val userPostItem = mutableListOf<PostModels>()

    fun getUserPost(postItem: PostModels) {
        if(userPostItem.isNotEmpty()) {
            userPostItem.clear()
            userPostItem.add(postItem)

        } else {
            userPostItem.add(postItem)
        }
    }
}