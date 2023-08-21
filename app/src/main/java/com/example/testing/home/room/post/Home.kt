package com.example.testing.home.room.post

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testing.utils.ISSTORY

@Entity(tableName = "home_posts")
data class Home (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userImage: String,
    val userLocation: String,
    val userName: String,
    val userPost: String,
    var isLike: Boolean,
    var isSave: Boolean
)