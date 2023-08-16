package com.example.testing.home.repository

import com.example.testing.R
import com.example.testing.home.ISSTORY
import com.example.testing.home.model.HomeModel
import com.example.testing.home.model.PostModel
import com.example.testing.home.model.StoryModel

class HomeRepository {

    val homePost = mutableListOf<HomeModel>()
    val postSubList = ArrayList<PostModel>()
    val storySubList = ArrayList<StoryModel>()

    fun addPost() {
        if(homePost.isEmpty()) {

            postSubList.add(PostModel(1, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false))
            postSubList.add(PostModel(1, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false))
            postSubList.add(PostModel(1, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false))
            postSubList.add(PostModel(1, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false))
            postSubList.add(PostModel(1, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false))

            storySubList.add(StoryModel(R.drawable.flower))
            storySubList.add(StoryModel(R.drawable.flower))
            storySubList.add(StoryModel(R.drawable.flower))
            storySubList.add(StoryModel(R.drawable.flower))
            storySubList.add(StoryModel(R.drawable.flower))
            storySubList.add(StoryModel(R.drawable.flower))
            storySubList.add(StoryModel(R.drawable.flower))
            storySubList.add(StoryModel(R.drawable.flower))
            storySubList.add(StoryModel(R.drawable.flower))
            storySubList.add(StoryModel(R.drawable.flower))


            homePost.add(HomeModel(1, R.drawable.image, R.drawable.flower, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.TRUE, null, storySubList))
            homePost.add(HomeModel(2, R.drawable.image, null, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.FALSE, postSubList, null))

        }
    }

    fun getPost(): List<HomeModel> {
        return homePost
    }
}