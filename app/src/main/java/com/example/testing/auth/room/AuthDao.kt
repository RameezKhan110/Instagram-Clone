package com.example.testing.auth.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AuthDao {

    @Insert
    suspend fun registerUser(registerUser: Auth)

    @Query("SELECT * FROM auth_table WHERE userEmail = :userEmail AND userPassword = :userPassword")
    suspend fun loginUser(userEmail: String, userPassword: String): Auth?

    @Query("SELECT * FROM auth_table WHERE userEmail = :userEmail")
    fun isEmailRegistered(userEmail: String): LiveData<Auth>
}