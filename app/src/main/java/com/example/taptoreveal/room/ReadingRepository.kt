package com.example.taptoreveal.room

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadingRepository(private val readingModelDao: ReadingModelDao) {
    val allReading = MutableLiveData<List<ReadingModel>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addReading(newReading : ReadingModel){
        coroutineScope.launch (Dispatchers.IO){
            readingModelDao.upsetReading(newReading)
        }
    }
}