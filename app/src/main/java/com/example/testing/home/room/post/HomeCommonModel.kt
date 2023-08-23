package com.example.testing.home.room.post

import com.example.testing.home.room.post.story.Story
import com.example.testing.model.Urls
import com.example.testing.model.Wallpapers
import com.example.testing.utils.ISSTORY
import com.example.testing.utils.Resource

sealed class HomeCommonModel {
    data class PostModelItem(val postItem: Resource<List<Wallpapers>>): HomeCommonModel()
    data class StoryModelItem(val storyItem: List<Story>): HomeCommonModel()

}
