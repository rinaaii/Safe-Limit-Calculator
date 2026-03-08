package com.example.safelimitcalculator.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorTokens(
    val background: Color,
    val surface: Color,
    val primaryAccent: Color,
    val secondaryAccent: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val error: Color,
    val success: Color,
    val warning: Color,
    val divider: Color,
    val inputBackground: Color,
    val iconActive: Color,
    val iconInactive: Color
)

val DarkColorTokens = ColorTokens(
    background = Color(0xFF181C1F),
    surface = Color(0xFF23282C),
    primaryAccent = Color(0xFF17D96E),
    secondaryAccent = Color(0xFF0E7A3D),
    textPrimary = Color(0xFFF4F8F7),
    textSecondary = Color(0xFFA7B8B4),
    error = Color(0xFFFF5252),
    success = Color(0xFF1ED760),
    warning = Color(0xFFFFC857),
    divider = Color(0xFF2B3237),
    inputBackground = Color(0xFF22282B),
    iconActive = Color(0xFF17D96E),
    iconInactive = Color(0xFF4B5A5B)
)
