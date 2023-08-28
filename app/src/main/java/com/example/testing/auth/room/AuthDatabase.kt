package com.example.testing.auth.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testing.home.room.post.story.StoryDatabase
import com.example.testing.utils.ApplicationClass

@Database(entities = [Auth::class], version = 1)
abstract class AuthDatabase: RoomDatabase() {

    abstract fun authDao(): AuthDao

    companion object {
        var INSTANCE: AuthDatabase? = null
        fun getDatabase(): AuthDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        ApplicationClass.application.baseContext,
                        AuthDatabase::class.java,
                        "authdatabase"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }
}