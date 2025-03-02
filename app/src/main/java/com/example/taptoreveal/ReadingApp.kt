package com.example.taptoreveal

import android.app.Application
import com.example.taptoreveal.room.ReadingDatabase

class ReadingApp : Application() {
    val db by lazy {
        ReadingDatabase.getInstance(this)
    }
}