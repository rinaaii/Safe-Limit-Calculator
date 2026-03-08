package com.example.safelimitcalculator.ui.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object LocalAppTheme {

    val colors: ColorTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalColorTokens.current

    val typography: TypographyTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalTypographyTokens.current

    val shapes: ShapeTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalShapeTokens.current

    val dimens: DimensionTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensionTokens.current


    val textFieldColors: TextFieldColors
        @Composable
        get() = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colors.textPrimary,
            unfocusedTextColor = colors.textPrimary,
            disabledTextColor = colors.textPrimary,

            focusedContainerColor = colors.inputBackground,
            unfocusedContainerColor = colors.inputBackground,
            disabledContainerColor = colors.inputBackground,

            focusedBorderColor = colors.primaryAccent,
            unfocusedBorderColor = colors.divider,
            disabledBorderColor = colors.divider,

            focusedLabelColor = colors.primaryAccent,
            unfocusedLabelColor = colors.textSecondary,
            disabledLabelColor = colors.textSecondary,

            cursorColor = colors.primaryAccent,
            selectionColors = TextSelectionColors(
                handleColor = colors.primaryAccent,
                backgroundColor = colors.primaryAccent.copy(alpha = 0.4f)
            )
        )
}