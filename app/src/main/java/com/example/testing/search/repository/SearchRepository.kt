package com.example.testing.search.repository

import com.example.testing.R
import com.example.testing.search.model.SearchModel

class SearchRepository {

    private val postsLists = mutableListOf<SearchModel>()

    fun addRandomPosts() {

        if(postsLists.isEmpty()) {
            postsLists.add(SearchModel(1, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(2, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(3, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(4, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(5, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(6, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(7, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(8, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(9, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(10, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(11, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(12, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
            postsLists.add(SearchModel(13, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.deer, false, false))
        }

    }

    fun getRandomPosts(): List<SearchModel> {
        return postsLists
    }
}