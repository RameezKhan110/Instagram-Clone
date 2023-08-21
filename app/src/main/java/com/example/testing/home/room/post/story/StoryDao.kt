package com.example.testing.home.room.post.story

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StoryDao {

    @Insert
    suspend fun addStory(story: Story)

    @Query("SELECT * FROM home_stories")
    fun getStory(): LiveData<List<Story>>
}