package com.example.testing.auth.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(user: User)

//    @Query("SELECT ")
//    fun deleteUser(user: User)
}