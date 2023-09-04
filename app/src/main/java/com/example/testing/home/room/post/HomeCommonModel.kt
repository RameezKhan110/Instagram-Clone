package com.example.testing.home.room.post

import com.example.testing.home.firestore.repository.model.FireStoreStory
import com.example.testing.home.model.PostModel
import com.example.testing.home.model.StoryModel
import com.example.testing.home.room.post.story.Story

sealed class HomeCommonModel {
    data class PostModelItem(val postItem: List<PostModel>): HomeCommonModel()
    data class StoryModelItem(val storyItem: List<StoryModel>): HomeCommonModel()

}
