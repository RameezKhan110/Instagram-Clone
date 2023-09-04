package com.example.testing.home.firestore.repository.model

data class FireStoreStory(val storyId: String, val story: String) {
    constructor(): this("", "")
}
