package com.example.taptoreveal.cardScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun CrystalBallReveal(interpretation: String) {
    var isRevealed by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition()
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Simulated AI processing delay
    LaunchedEffect(true) {
        delay(1500)
        isRevealed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(300.dp)
            .clickable { isRevealed = false }
    ) {
        // Crystal Ball
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0xFF6B8DD6), Color(0xFF1A237E))
                    ),
                    CircleShape
                )
                .blur(16.dp)
        )

        // Inner Glow Effect
        Box(
            modifier = Modifier
                .size(220.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = glowAlpha), Color.Transparent)
                    ),
                    CircleShape
                )
        )

        // Mist Effect before reveal
        if (!isRevealed) {
            SwirlingMistEffect()
        }

        // Text Reveal
        AnimatedVisibility(
            visible = isRevealed,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Text(
                text = interpretation,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(32.dp)
                    .graphicsLayer(rotationZ = (-2 + (4 * glowAlpha)))
            )
        }
    }
}

@Composable
private fun SwirlingMistEffect() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing)
        )
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .rotate(rotation)
            .background(
                Brush.sweepGradient(
                    colors = listOf(Color.Transparent, Color.White.copy(alpha = 0.2f), Color.Transparent)
                ),
                CircleShape
            )
    )
}
