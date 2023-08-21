package com.example.testing.profile.saved_post.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testing.home.model.PostModel
import com.example.testing.home.room.post.Home

class SavedSharedViewModel: ViewModel() {

    val itemList = mutableListOf<Home>()

    fun getUserPost(postItem: Home) {
        itemList.add(postItem)
    }
}