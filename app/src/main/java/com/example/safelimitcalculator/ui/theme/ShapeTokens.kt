package com.example.safelimitcalculator.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

@Immutable
data class ShapeTokens(
    val sm: RoundedCornerShape,
    val md: RoundedCornerShape,
    val lg: RoundedCornerShape
)

val AppShapes = ShapeTokens(
    sm = RoundedCornerShape(6.dp),
    md = RoundedCornerShape(12.dp),
    lg = RoundedCornerShape(20.dp)
)