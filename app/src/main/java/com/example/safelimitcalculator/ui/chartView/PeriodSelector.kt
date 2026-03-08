package com.example.safelimitcalculator.ui.chartView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.example.safelimitcalculator.utils.AnalyticsPeriod

@Composable
fun PeriodSelector(
    selected: AnalyticsPeriod,
    onSelect: (AnalyticsPeriod) -> Unit
) {
    val colors = LocalAppTheme.colors
    val dimensions = LocalAppTheme.dimens
    val shapes = LocalAppTheme.shapes

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensions.sm)
    ) {
        AnalyticsPeriod.values().forEach { period ->
            val isSelected = period == selected
            Button(
                onClick = { onSelect(period) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) colors.primaryAccent else colors.inputBackground,
                    contentColor = if (isSelected) colors.background else colors.textPrimary
                ),
                shape = shapes.md,
                contentPadding = PaddingValues(horizontal = dimensions.md, vertical = dimensions.xs)
            ) {
                Text(
                    text = period.name,
                    style = LocalAppTheme.typography.body
                )
            }
        }
    }
}
