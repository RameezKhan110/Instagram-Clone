package com.example.testing.profile.saved_post.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testing.home.model.HomeModel

class SavedSharedViewModel: ViewModel() {

    val itemList = mutableListOf<HomeModel>()

    fun getUserWholePost(postItem: HomeModel) {
        itemList.add(postItem)
    }
}