package com.example.testing.home.room.post

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HomeDao {

    @Insert
    suspend fun addPost(posts: Home)

    @Query("SELECT * FROM home_posts")
    fun getPost(): LiveData<List<Home>>

    @Query("DELETE FROM home_posts")
    suspend fun dropTable()
}