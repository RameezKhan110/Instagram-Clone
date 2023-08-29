package com.example.testing.auth.room

import androidx.room.Embedded
import com.example.testing.home.room.post.story.Story

data class CombinedUserData(
    @Embedded
    val authData: Auth,
    @Embedded
    val storyData: Story
)
