package com.example.testing.profile.saved_post.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testing.search.model.SearchModel

class DetailToSavedSharedViewModel: ViewModel() {

    val itemList = mutableListOf<SearchModel>()

    fun getUserWholePost(postItem: SearchModel) {
        itemList.add(postItem)
    }

}