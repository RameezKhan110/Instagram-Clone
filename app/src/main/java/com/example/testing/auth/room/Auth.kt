package com.example.testing.auth.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_table")
data class Auth(

    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val userImage: String,
    val userName: String,
    val userEmail: String,
    val userPassword: String
)
