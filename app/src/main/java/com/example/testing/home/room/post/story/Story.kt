package com.example.testing.home.room.post.story

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testing.utils.ISSTORY

@Entity(tableName = "home_stories")
data class Story(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val usersId: Int,
    val story: String
)
