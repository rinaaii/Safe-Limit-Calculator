package com.example.safelimitcalculator.ui.chartView

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun PieChartView(data: List<Pair<String, Float>>) {
    val colors = LocalAppTheme.colors

    val chartColors = listOf(
        colors.primaryAccent.toArgb(),
        colors.secondaryAccent.toArgb(),
        colors.success.toArgb(),
        colors.warning.toArgb(),
        colors.error.toArgb()
    )

    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false
                setHoleColor(colors.background.toArgb())
                legend.apply {
                    isEnabled = true
                    textColor = colors.textPrimary.toArgb()
                    textSize = 14f
                    formSize = 14f
                    formToTextSpace = 8f
                    xEntrySpace = 12f
                }
                setTransparentCircleColor(colors.background.toArgb())
                setEntryLabelColor(colors.textPrimary.toArgb())
                legend.textColor = colors.textPrimary.toArgb()
                setHoleColor(0)
            }
        },
        update = { chart ->
            val entries = data.map { PieEntry(it.second, it.first) }
            val dataSet = PieDataSet(entries, "").apply {
                this.colors = chartColors
                valueTextColor = colors.background.toArgb()
                valueTextSize = 12f
                sliceSpace = 2f
            }

            chart.data = PieData(dataSet)
            chart.invalidate()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}
