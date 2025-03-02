package com.example.taptoreveal.cardScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.taptoreveal.R
import com.example.taptoreveal.TarotViewModel
import com.example.tarotapp.data.CardData


@Composable
fun cardScreen(
    modifier: Modifier,
    viewModel: TarotViewModel = viewModel(),
    navController: NavController
) {
    val cards = viewModel.cards.collectAsState()
    var selectedCard by remember { mutableStateOf<List<CardData>>(emptyList()) }
    var randomCards = remember { mutableStateListOf<CardData>() }
    LaunchedEffect(Unit) {
        viewModel.fetchCard()
    }
    var visible by remember { mutableStateOf(true) }
    Column (modifier = Modifier.fillMaxSize()){
        Column(modifier=modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            if (visible){
                Text(text = "Select any 3 cards !!",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.secondary)

            }

            vanishAnimation2(visible,modifier= Modifier.weight(1f))


        }

        IconButton(onClick ={
            visible = false
        },
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)) {
            Image(painter = painterResource(
                if(visible) R.drawable.reading
                else R.drawable.baseline_question_answer_24
            ),
                contentDescription = "reading")
        }
        Button(onClick = {
             navController.navigate("chat")
        },
            modifier = Modifier.fillMaxWidth()) {
            Text("start chat")
        }


    }
}


@Composable
fun vanishAnimation2(visible : Boolean,modifier: Modifier){

    AnimatedContent(
        targetState = visible,
        modifier = modifier.fillMaxSize(),
        content = {isVisible ->
            if(isVisible) ShufflingDeck(modifier)
            else {
                CrystalBallReveal("reading")
                MysticalBackground()
            }

        },
        transitionSpec = {
            slideInVertically { height -> height } + fadeIn(
                tween(delayMillis = 20, durationMillis = 1000)
            ) togetherWith
                    slideOutVertically { height -> -height } + fadeOut()
        }
    )

}

