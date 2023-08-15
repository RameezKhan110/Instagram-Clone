package com.example.testing.profile.timeline_post.model

data class PostModel(val postId: Int, val userImage: Int, val userName: String, val userLocation: String, val userPost: Int, var isLike: Boolean, var isSave: Boolean)
