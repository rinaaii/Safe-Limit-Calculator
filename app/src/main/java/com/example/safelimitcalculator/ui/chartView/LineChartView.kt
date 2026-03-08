package com.example.safelimitcalculator.ui.chartView

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import org.threeten.bp.LocalDate
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun LineChartView(data: List<Pair<LocalDate, Float>>) {
    val colors = LocalAppTheme.colors

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                xAxis.textColor = colors.textSecondary.toArgb()
                axisLeft.textColor = colors.textSecondary.toArgb()
                axisRight.isEnabled = false
                xAxis.gridColor = colors.divider.toArgb()
                axisLeft.gridColor = colors.divider.toArgb()
                legend.textColor = colors.textPrimary.toArgb()
                setNoDataTextColor(colors.textSecondary.toArgb())
            }
        },
        update = { chart ->
            val entries = data.mapIndexed { index, pair -> Entry(index.toFloat(), pair.second) }
            val dataSet = LineDataSet(entries, "Daily Expenses").apply {
                color = colors.primaryAccent.toArgb()
                setCircleColor(colors.primaryAccent.toArgb())
                valueTextColor = colors.textPrimary.toArgb()
                lineWidth = 2f
                circleRadius = 4f
                setDrawCircleHole(false)
                setDrawValues(false)
            }

            chart.data = LineData(dataSet)
            chart.invalidate()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}