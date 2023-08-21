package com.example.testing.home.room.post.story

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testing.utils.ApplicationClass

@Database(entities = [Story::class], version = 1)
abstract class StoryDatabase: RoomDatabase() {

    abstract fun storyDao(): StoryDao

    companion object {
        var INSTANCE: StoryDatabase? = null
        fun getDatabase(): StoryDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        ApplicationClass.application.baseContext,
                        StoryDatabase::class.java,
                        "mystory"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }
}