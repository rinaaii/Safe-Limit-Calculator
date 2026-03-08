package com.example.safelimitcalculator.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun SafeExpenseTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColorTokens provides DarkColorTokens,
        LocalTypographyTokens provides AppTypography,
        LocalShapeTokens provides AppShapes,
        LocalDimensionTokens provides AppDimensions
    ) {
        MaterialTheme(
            content = content
        )
    }
}