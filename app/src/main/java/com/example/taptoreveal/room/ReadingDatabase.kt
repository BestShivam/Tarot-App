package com.example.taptoreveal.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ReadingModel::class], version = 1)
abstract class ReadingDatabase : RoomDatabase() {
    abstract val dao : ReadingModelDao
    companion object {
        @Volatile
        private var INSTANCE : ReadingDatabase? = null
        fun getInstance(context: Context) : ReadingDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ReadingDatabase::class.java,
                        "reading-database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

