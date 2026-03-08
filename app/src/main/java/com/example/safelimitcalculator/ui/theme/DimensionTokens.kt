package com.example.safelimitcalculator.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class DimensionTokens(
    val xs: Dp,
    val sm: Dp,
    val md: Dp,
    val lg: Dp,
    val xl: Dp,

    val screenPadding: Dp,
    val cardPadding: Dp,
    val progressHeight: Dp
)

val AppDimensions = DimensionTokens(
    xs = 4.dp,
    sm = 8.dp,
    md = 16.dp,
    lg = 24.dp,
    xl = 32.dp,

    screenPadding = 16.dp,
    cardPadding = 16.dp,
    progressHeight = 6.dp
)