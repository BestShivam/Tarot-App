package com.example.chartbot

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taptoreveal.R
import com.example.taptoreveal.chatPage.MessageModel
import com.example.taptoreveal.ui.theme.modelColorMsg
import com.example.taptoreveal.ui.theme.noMsgTextColor
import com.example.taptoreveal.ui.theme.userColorMsg


@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun chartPage(modifier: Modifier,viewModel: ChatViewModel) {

    val messagesList by viewModel.messageList
        .observeAsState()
    Column (modifier = modifier.fillMaxSize()){
            appHeader(modifier = modifier)
        messagesList?.let {
            messageList(modifier = modifier.weight(1f),
                it
            )
        }
            messageInput{
            viewModel.sendMessage(it)
            }
    }
}

@Composable
fun appHeader(modifier: Modifier){
    Box(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary)) {
        Text("Chat Bot",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun messageInput(onMessageSend : (String) -> Unit){
    var message by remember { mutableStateOf("") }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        OutlinedTextField(
            value = message,
            onValueChange = {
                message = it
            },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        )
        IconButton(onClick = {
            if(message.isNotEmpty()) {
                onMessageSend(message)
                message = ""
            }
        }) {
            Icon(imageVector = Icons.Default.Send,
                contentDescription = "send",
                modifier = Modifier.size(30.dp)
                )
        }
    }
}

@Composable
fun messageList(modifier: Modifier ,
                messageList: List<MessageModel>){
    if(messageList.isEmpty()){
        Column(modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter =
            painterResource(R.drawable.baseline_question_answer_24),
                contentDescription = "no message",
                tint = noMsgTextColor,
                modifier = Modifier.size(100.dp))
            Text("Ask me anything",
                style = MaterialTheme.typography.displayMedium,
                color = noMsgTextColor)
        }
    }
    LazyColumn (modifier = modifier
        .fillMaxWidth(),
        reverseLayout = true){
        items(messageList.reversed()){
            messageDesign(it)
        }
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun messageDesign(messageModel: MessageModel){
    val isModel = messageModel.role == "model"

    Box (modifier = Modifier.fillMaxWidth()){
        Box (modifier = Modifier
            .padding(
                start = if (isModel) 8.dp else 2.dp,
                end = if (isModel) 2.dp else 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .align(
                if (isModel) Alignment.BottomStart
                else Alignment.BottomEnd
            )
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isModel) userColorMsg
                else modelColorMsg
            )
            ){
            SelectionContainer {
                Text(messageModel.message,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White)
            }
        }
    }
}