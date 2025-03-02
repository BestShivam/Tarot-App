package com.example.taptoreveal

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import com.example.taptoreveal.room.ReadingModel

@Composable
fun pastReadingScreen() {
    Column (modifier = Modifier.fillMaxSize() ){
        Text("Past Reading Screen")
    }
}