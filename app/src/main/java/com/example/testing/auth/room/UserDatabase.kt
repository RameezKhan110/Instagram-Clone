package com.example.testing.auth.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testing.utils.ApplicationClass


@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase(){

    abstract fun userDao(): UserDao

    companion object {
        var INSTANCE: UserDatabase? = null
        fun getDatabase(): UserDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        ApplicationClass.application.baseContext,
                        UserDatabase::class.java,
                        "userdatabase"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }


}