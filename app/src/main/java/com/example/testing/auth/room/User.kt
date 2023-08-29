package com.example.testing.auth.room

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_details")
data class User(
    @PrimaryKey(autoGenerate = false)
    val userGoogleId: String,
    val userGoogleImage: String,
    val userGoogleName: String?,
    val userGoogleEmail: String?
)
