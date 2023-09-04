package com.example.testing.auth.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testing.home.room.post.story.Story

@Dao
interface AuthDao {

    @Insert
    suspend fun registerUser(registerUser: Auth)

    @Query("SELECT * FROM auth_table WHERE userEmail = :userEmail AND userPassword = :userPassword")
    suspend fun loginUser(userEmail: String, userPassword: String): Auth?

    @Query("SELECT * FROM auth_table WHERE userEmail = :userEmail")
    fun isEmailRegistered(userEmail: String): LiveData<Auth>

    @Query("SELECT * FROM auth_table WHERE userEmail = :userEmail")
    fun getDataAsPerEmail(userEmail: String): LiveData<List<Auth>>

    @Query("SELECT userId from auth_table WHERE userEmail = :userEmail")
    fun getUserId(userEmail: String): LiveData<Int>

    @Query("SELECT * FROM home_stories WHERE usersId = :userId")
    fun getStoryAsPerId(userId: Int): LiveData<List<Story>>

}