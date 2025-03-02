package com.example.taptoreveal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarotapp.data.CardData
import com.example.tarotapp.data.Repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TarotViewModel() : ViewModel() {

    private val _cards =
        MutableStateFlow<List<CardData>>(emptyList())
    val cards : StateFlow<List<CardData>> = _cards

    fun fetchCard(){
        viewModelScope.launch {
            try {
                _cards.value = Repository.getCards()
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }
}