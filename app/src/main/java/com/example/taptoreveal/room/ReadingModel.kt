package com.example.taptoreveal.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading_model_table")
data class ReadingModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val date : String = "",
    val reading : String =""
)