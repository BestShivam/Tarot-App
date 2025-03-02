package com.example.chartbot

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taptoreveal.chatPage.Constants
import com.example.taptoreveal.chatPage.MessageModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    // Backing property as MutableLiveData, initialized with an empty list.
    private val _messageList = MutableLiveData<List<MessageModel>>(emptyList())
    val messageList: LiveData<List<MessageModel>> = _messageList

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun sendMessage(question: String) {
        val generativeModel = GenerativeModel(
            modelName = "gemini-2.0-pro-exp-02-05",
            apiKey = Constants.apikey
        )
        viewModelScope.launch (Dispatchers.IO){
            try {
                addMessage(MessageModel(question, "user"),true)
                val chat = _messageList.value?.let {
                    generativeModel.startChat(
                        history = it.map {
                            content(role = it.role){
                                text(it.message)
                            }
                        }.toList()
                    )
                }
                val response = chat?.sendMessage(question)
                addMessage(MessageModel(response?.text.toString(), "model"),false)
                Log.d("ttt", "list : ${_messageList.value}")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("ttt", "error : ${e.message}")
                addMessage(MessageModel("error : ${e.message}","model"),false)
            }
        }
    }

    // Helper function to update the message list.
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun addMessage(message: MessageModel, isTyping : Boolean) {
        val currentList = _messageList.value.orEmpty().toMutableList()
        if(isTyping) {
            currentList.add(message)
            currentList.add(MessageModel("Typing...", role = "model"))
        }
        else{
            if(currentList.isNotEmpty()){ currentList.removeLast() }
            currentList.add(message)
        }
        // Update LiveData with the new list.
        _messageList.postValue(currentList)
    }

}
