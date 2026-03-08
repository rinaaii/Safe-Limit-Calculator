package com.example.safelimitcalculator.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.example.safelimitcalculator.ui.viewmodel.AnalyticsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.safelimitcalculator.R
import com.example.safelimitcalculator.ui.chartView.LineChartView
import com.example.safelimitcalculator.ui.chartView.PeriodSelector
import com.example.safelimitcalculator.ui.chartView.PieChartView

@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val dimens = LocalAppTheme.dimens
    val colors = LocalAppTheme.colors
    val typography = LocalAppTheme.typography
    val shapes = LocalAppTheme.shapes

    val scroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colors.primaryAccent
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimens.screenPadding)
                    .verticalScroll(scroll),
                verticalArrangement = Arrangement.spacedBy(dimens.lg)
            ) {
                Text(
                    text = stringResource(R.string.analytics),
                    style = typography.headline1,
                    color = colors.textPrimary
                )

                PeriodSelector(
                    selected = uiState.selectedPeriod,
                    onSelect = { viewModel.onPeriodChanged(it) }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(stringResource(R.string.total_spent), style = typography.body, color = colors.textSecondary)
                        Text(
                            text = "$${"%.2f".format(uiState.totalAmount)}",
                            style = typography.headline2,
                            color = colors.primaryAccent
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(stringResource(R.string.avg_per_day), style = typography.body, color = colors.textSecondary)
                        Text(
                            text = "$${"%.2f".format(uiState.averagePerDay)}",
                            style = typography.headline2,
                            color = colors.secondaryAccent
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(dimens.sm)) {
                    Text(stringResource(R.string.daily_expenses), style = typography.headline2, color = colors.textPrimary)
                    Surface(
                        shape = shapes.md,
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.divider)
                    ) {
                        Box(modifier = Modifier.padding(dimens.md)) {
                            LineChartView(uiState.dailyData)
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(dimens.sm)) {
                    Text(stringResource(R.string.by_category), style = typography.headline2, color = colors.textPrimary)
                    Surface(
                        shape = shapes.md,
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.divider)
                    ) {
                        Box(modifier = Modifier.padding(dimens.md)) {
                            PieChartView(uiState.categoryData)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(dimens.xl))
            }
        }
    }
}

