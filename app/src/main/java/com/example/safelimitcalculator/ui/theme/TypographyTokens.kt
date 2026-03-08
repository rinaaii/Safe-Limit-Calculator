package com.example.safelimitcalculator.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class TypographyTokens(
    val headline1: TextStyle,
    val headline2: TextStyle,
    val body: TextStyle,
    val bodyBold: TextStyle,
    val caption: TextStyle,
    val button: TextStyle
)

val AppTypography = TypographyTokens(
    headline1 = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),
    headline2 = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
    body = TextStyle(fontSize = 15.sp),
    bodyBold = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Medium),
    caption = TextStyle(fontSize = 13.sp),
    button = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Medium)
)