package com.example.testing.home.model

import com.example.testing.home.ISSTORY

data class HomeModel(val postId: Int, val userImage: Int, val userStory: Int?, val userName: String, val userLocation: String, val userPost: Int, var isLike: Boolean, var isSave: Boolean, var isStory: ISSTORY, val postLists: ArrayList<PostModel>?, val storyList: ArrayList<StoryModel>?)