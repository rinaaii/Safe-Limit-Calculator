package com.example.safelimitcalculator.ui.screens

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.safelimitcalculator.R
import com.example.safelimitcalculator.data.InAppReviewManager
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.example.safelimitcalculator.ui.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val colors = LocalAppTheme.colors
    val typography = LocalAppTheme.typography
    val dimens = LocalAppTheme.dimens
    val shapes = LocalAppTheme.shapes

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val context = LocalContext.current
    val activity = context as? Activity
    val reviewManager = remember { InAppReviewManager(context) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .padding(horizontal = dimens.screenPadding)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colors.primaryAccent
            )
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimens.md),
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.settings),
                    style = typography.headline1,
                    color = colors.textPrimary
                )

                OutlinedTextField(
                    value = uiState.reserve,
                    onValueChange = { viewModel.onReserveChanged(it) },
                    label = { Text(stringResource(R.string.minimum_reserve)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = shapes.md,
                    colors = LocalAppTheme.textFieldColors
                )

                OutlinedTextField(
                    value = uiState.currentBalance,
                    onValueChange = { viewModel.onBalanceChanged(it) },
                    label = { Text(stringResource(R.string.current_balance)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = shapes.md,
                    colors = LocalAppTheme.textFieldColors
                )

                OutlinedTextField(
                    value = uiState.nextIncomeDate?.toString() ?: "",
                    onValueChange = {},
                    readOnly = true,
                    enabled = false,
                    label = { Text(stringResource(R.string.next_income_date)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    shape = shapes.md,
                    colors = LocalAppTheme.textFieldColors
                )

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    datePickerState.selectedDateMillis?.let {
                                        val selectedDate = Instant.ofEpochMilli(it)
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDate()
                                        viewModel.onDateChanged(selectedDate)
                                    }
                                    showDatePicker = false
                                }
                            ) { Text(stringResource(R.string.ok), color = colors.primaryAccent) }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("Cancel", color = colors.textSecondary)
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimens.sm),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Notifications",
                        style = typography.body,
                        color = colors.textPrimary
                    )
                    Switch(
                        checked = uiState.notificationsEnabled,
                        onCheckedChange = { viewModel.onNotificationsChanged(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.primaryAccent,
                            checkedTrackColor = colors.primaryAccent.copy(alpha = 0.5f),
                            uncheckedThumbColor = colors.textSecondary,
                            uncheckedTrackColor = colors.inputBackground
                        )
                    )
                }

                Button(
                    onClick = {
                        activity?.let {
                            reviewManager.launchReview(it)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = shapes.md,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primaryAccent.copy(alpha = 0.1f),
                        contentColor = colors.primaryAccent
                    ),
                    border = BorderStroke(1.dp, colors.primaryAccent)
                ) {
                    Text(stringResource(R.string.rate_app), style = typography.headline2)
                }

                val context = LocalContext.current

                Button(
                    onClick = { viewModel.saveSettings(context) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = shapes.md,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primaryAccent.copy(alpha = 0.1f),
                        contentColor = colors.primaryAccent
                    ),
                    border = BorderStroke(1.dp, colors.primaryAccent)
                ) {
                    Text(stringResource(R.string.save_settings), style = typography.headline2)
                }
            }
        }
    }
}
