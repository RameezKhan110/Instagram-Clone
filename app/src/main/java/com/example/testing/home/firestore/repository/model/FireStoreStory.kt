package com.example.testing.home.firestore.repository.model

data class FireStoreStory(val userId: String, val storyId:String, val story: String) {
    constructor(): this("", "", "")
}
