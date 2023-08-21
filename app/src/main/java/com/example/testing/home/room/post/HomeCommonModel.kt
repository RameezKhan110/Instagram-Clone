package com.example.testing.home.room.post

import com.example.testing.home.room.post.story.Story
import com.example.testing.utils.ISSTORY

sealed class HomeCommonModel {
    data class PostModelItem(val postItem: Home): HomeCommonModel()
    data class StoryModelItem(val storyItem: List<Story>): HomeCommonModel()

}
