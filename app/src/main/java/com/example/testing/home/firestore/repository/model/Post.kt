package com.example.testing.home.firestore.repository.model

data class Post(val postId: String, val userPost: String) {
    constructor(): this("", "")
}
