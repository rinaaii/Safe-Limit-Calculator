package com.example.safelimitcalculator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.safelimitcalculator.ui.theme.LocalAppTheme

@Composable
fun LimitIndicator(
    safeLimit: Float,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppTheme.colors
    val typography = LocalAppTheme.typography
    val dimens = LocalAppTheme.dimens

    Card(
        shape = RoundedCornerShape(dimens.md),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(dimens.md)) {
            Text(
                text = "Safe Daily Limit",
                style = typography.headline2,
                color = colors.textPrimary
            )
            Spacer(modifier = Modifier.height(dimens.xs))
            Text(
                text = "$${"%.2f".format(safeLimit)}",
                style = typography.headline1,
                color = if (safeLimit > 0) colors.success else colors.error
            )
        }
    }
}