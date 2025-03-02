package com.example.taptoreveal.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface ReadingModelDao {
    @Query("SELECT * FROM 'reading_model_table'")
    fun getAllRecord():Flow<List<ReadingModel>>

    @Upsert
    suspend fun upsetReading(readingModel: ReadingModel)
}