package com.example.safelimitcalculator.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun GradientMotionAnimation(primaryColor: Color, secondaryColor: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "gradient")

    val xOffset by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "x"
    )

    Canvas(modifier = Modifier.fillMaxSize().alpha(0.15f)) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(primaryColor, Color.Transparent),
                center = Offset(xOffset, size.height / 2f),
                radius = 900f
            ),
            radius = 900f,
            center = Offset(xOffset, size.height / 2f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(secondaryColor, Color.Transparent),
                center = Offset(size.width - xOffset, size.height / 4f),
                radius = 700f
            ),
            radius = 700f,
            center = Offset(size.width - xOffset, size.height / 4f)
        )
    }
}