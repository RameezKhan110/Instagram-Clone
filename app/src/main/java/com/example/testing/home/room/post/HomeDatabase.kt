package com.example.testing.home.room.post

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testing.utils.ApplicationClass

@Database(entities = [Home::class], version = 1)
//@TypeConverters(CustomConverter::class)
abstract class HomeDatabase : RoomDatabase() {

    abstract fun homeDao(): HomeDao

    companion object {

        private var INSTANCE: HomeDatabase? = null
        fun getDatabase(): HomeDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        ApplicationClass.application.baseContext,
                        HomeDatabase::class.java,
                        "myposts"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}