package com.example.testing.profile.saved_post.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testing.home.model.HomeModel
import com.example.testing.home.model.PostModel

class SavedSharedViewModel: ViewModel() {

    val itemList = mutableListOf<PostModel>()

    fun getUserWholePost(postItem: PostModel) {
        itemList.add(postItem)
    }
}