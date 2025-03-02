package com.example.taptoreveal.cardScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tarotapp.data.CardData
import com.example.tarotapp.data.Repository
import kotlinx.coroutines.delay
import kotlin.random.Random

data class CardState(
    val id: Int,
    var targetOffsetX: Dp,
    var targetOffsetY: Dp,
    var targetRotation: Float
)

@Preview(showBackground = true)
@Composable
fun ShufflingDeck(
        modifier: Modifier = Modifier,
) {
    val cards = remember { mutableStateListOf<CardState>() }
    var shuffleTrigger by remember { mutableIntStateOf(0) }
    var visibleCards by remember { mutableIntStateOf(0) } // Controls visible card count (0,1,2,3)
    var randomCards = remember { mutableStateListOf<CardData>() }

    // Initialize deck
    LaunchedEffect(Unit) {
        repeat(52) { index ->
            cards.add(CardState(index, 0.dp, (index * 2).dp, 0f))
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .padding(top = 70.dp)
                .clickable {
                    if (visibleCards < 3) {
                        visibleCards++
                        shuffleTrigger++
                        randomCards.add((Repository.getCards()-randomCards).random())
                        Log.d("ddd","${randomCards.toString()}")
                    }
                }
        ) {
            cards.forEach { card ->
                key(card.id) {
                    AnimatedCard(card)
                }
            }
        }

        // Display animated random cards
        Row {
            repeat(3) { index ->
                if(index < randomCards.size){
                    RandomCardAnimation(visible = visibleCards > index,randomCards[index])
                }
            }
        }

    }

    // Shuffle animation
    LaunchedEffect(shuffleTrigger) {
        val animationDuration = 200L
        repeat(5) {
            cards.forEachIndexed { index, card ->
                card.targetOffsetX = Random.nextInt(-50, 50).dp
                card.targetOffsetY = Random.nextInt(-50, 50).dp
                card.targetRotation = Random.nextInt(-15, 15).toFloat()
            }
            delay(animationDuration)
        }
        // Reset deck position
        cards.forEachIndexed { index, card ->
            card.targetOffsetX = 0.dp
            card.targetOffsetY = (index * 2).dp
            card.targetRotation = 0f
        }
    }
}

@Composable
fun AnimatedCard(card: CardState) {
    val offsetX by animateDpAsState(targetValue = card.targetOffsetX,
        animationSpec = tween(200))
    val offsetY by animateDpAsState(targetValue = card.targetOffsetY,
        animationSpec = tween(200))
    val rotation by animateFloatAsState(targetValue = card.targetRotation,
        animationSpec = tween(200))

    Card (
        modifier = Modifier
            .offset(x = offsetX, y = offsetY)
            .rotate(rotation)
            .size(100.dp, 150.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        colors = CardDefaults.cardColors(colorResource(com.example.taptoreveal.R.color.card_back_color))
    ) {
        Text("Tarot Card", color = colorResource(com.example.taptoreveal.R.color.card_back_font_color),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(6.dp))
    }
}

@Composable
fun RandomCardAnimation(visible: Boolean,data: CardData) {
    var isFaceUp by remember { mutableStateOf(true) }
    val rotation = animateFloatAsState(
        targetValue = if (isFaceUp) 0f else 180f,
        animationSpec = tween(durationMillis = 600)
    )
    var cameraDistance = 12f
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically() + fadeIn(initialAlpha = 0.3f, animationSpec = tween(2000, 20)),
        exit = slideOutVertically() + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp, 150.dp)
                .clickable {
                    isFaceUp = !isFaceUp
                }
        ) {

            if (rotation.value < 90f) {
                CardFront(
                    Modifier
                        .graphicsLayer {
                            rotationY = rotation.value
                            cameraDistance = cameraDistance
                        }
                )
            }

            // Back of the card
            if (rotation.value > 90f) {
                CardBack(
                    Modifier
                        .graphicsLayer {
                            rotationY = rotation.value + 180f
                            cameraDistance = cameraDistance
                        },
                    data = data
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CardFront(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .size(100.dp,150.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        colors = CardDefaults.cardColors(colorResource(com.example.taptoreveal.R.color.card_back_color))
    ) {
        // Front content (e.g., card value/suit)
        Text("Tarot Card", modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun CardBack(modifier: Modifier = Modifier,data: CardData) {
    Card(
        modifier = modifier
            .size(100.dp,150.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
    ) {
        // Back pattern
        Box(
            Modifier
                .padding(4.dp)
        ){
            Text(data.name, modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium)
        }
    }
}