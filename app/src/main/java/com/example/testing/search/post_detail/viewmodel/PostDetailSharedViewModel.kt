package com.example.testing.search.post_detail.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testing.search.model.SearchModel

class PostDetailSharedViewModel: ViewModel() {

    val userPostItem = mutableListOf<SearchModel>()

    fun getUserPost(postItem: SearchModel) {
        if(userPostItem.isNotEmpty()) {
            userPostItem.clear()
            userPostItem.add(postItem)

        } else {
            userPostItem.add(postItem)
        }
    }
}