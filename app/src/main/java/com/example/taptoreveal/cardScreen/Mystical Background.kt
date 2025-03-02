package com.example.taptoreveal.cardScreen

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun MysticalBackground(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        // Base aura glow
        GlowingAuraEffect()

        // Floating stars
        FloatingStars2(count = 30)

    }
}

@Preview(showBackground = true)
@Composable
private fun GlowingAuraEffect() {
    val infiniteTransition = rememberInfiniteTransition()
    val auraPulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = size.center
        val radius = size.maxDimension * 0.4f * auraPulse

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFF6B8DD6).copy(alpha = 0.8f),
                    Color.Transparent
                ),
                center = center,
                radius = radius
            ),
            center = center,
            radius = radius
        )

        // Add subtle shadow effect
        drawCircle(
            color = Color(0xFF2E0368).copy(alpha = 0.8f),
            center = center,
            radius = radius * 1.2f,
            blendMode = BlendMode.Overlay
        )
    }
}


@Composable
private fun FloatingStars2(count: Int) {

    val stars = remember { List(count) {
        StarConfig(
            x = Random.nextFloat(),
            y = Random.nextFloat(),
            speed = 0.1f + Random.nextFloat() * 0.3f,
            size = 2.dp + 4.dp * Random.nextFloat()
        )
    }}

    val infiniteTransition = rememberInfiniteTransition()

    // Create animations for each star
    val yOffsets = stars.map { star ->
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = (3000 / star.speed).toInt(),
                    easing = LinearEasing
                )
            )
        )
    }

    Canvas(Modifier.fillMaxSize()) {
        stars.forEachIndexed { index, star ->
            val yOffset = yOffsets[index].value
            val currentY = star.y + yOffset
            val normalizedY = if (currentY > 1f) currentY - 1f else currentY

            drawCircle(
                color = Color.White,
                center = Offset(star.x * size.width, normalizedY * size.height),
                radius = star.size.toPx(),
                alpha = 0.3f + (1 - yOffset) * 0.7f,
                blendMode = BlendMode.Plus
            )
        }
    }
}


@Composable
private fun AnimatedFogLayer(
    rotationDirection: Float = 1f,
    speedMultiplier: Float = 1f
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f * rotationDirection,
        animationSpec = infiniteRepeatable(
            animation = tween(120000 / speedMultiplier.toInt(),
                easing = LinearEasing)
        )
    )

    Canvas(Modifier.fillMaxSize()) {
        val fogBrush = Brush.sweepGradient(
            colors = listOf(
                Color.Green,
                Color(0xFF9EB5E0).copy(alpha = 0.1f),
                Color.Black
            ),
            center = size.center
        )

        rotate(rotation) {
            drawCircle(
                brush = fogBrush,
                center = size.center,
                radius = size.minDimension * 0.8f,
                alpha = 0.3f
            )
        }
    }
}

private data class StarConfig(
    val x: Float,
    val y: Float,
    val speed: Float,
    val size: Dp
)