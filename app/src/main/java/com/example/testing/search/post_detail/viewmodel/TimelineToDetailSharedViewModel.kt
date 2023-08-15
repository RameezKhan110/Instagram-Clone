package com.example.testing.search.post_detail.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testing.profile.timeline_post.model.PostModel

class TimelineToDetailSharedViewModel: ViewModel() {

    val userPostItem = mutableListOf<PostModel>()

    fun getUserPost(postItem: PostModel) {
        if(userPostItem.isNotEmpty()) {
            userPostItem.clear()
            userPostItem.add(postItem)

        } else {
            userPostItem.add(postItem)
        }
    }
}