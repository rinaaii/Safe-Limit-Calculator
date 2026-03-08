package com.example.safelimitcalculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.fillMaxSize
import com.example.safelimitcalculator.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import org.threeten.bp.LocalDate
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.example.safelimitcalculator.ui.viewmodel.AddEditPaymentViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddEditPaymentScreen(
    paymentId: Long,
    navController: NavController,
    viewModel: AddEditPaymentViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val colors = LocalAppTheme.colors
    val typography = LocalAppTheme.typography
    val dimens = LocalAppTheme.dimens
    val shapes = LocalAppTheme.shapes

    val textFieldColors = LocalAppTheme.textFieldColors

    LaunchedEffect(Unit) {
        viewModel.loadPayment(paymentId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .padding(horizontal = dimens.screenPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimens.md)
        ) {
            Text(
                text = if (paymentId == -1L) stringResource(R.string.add_payment) else stringResource(
                    R.string.edit_payment
                ),
                style = typography.headline1,
                color = colors.textPrimary
            )

            OutlinedTextField(
                value = uiState.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Name", style = typography.body) },
                modifier = Modifier.fillMaxWidth(),
                shape = shapes.md,
                colors = textFieldColors
            )

            OutlinedTextField(
                value = uiState.amount,
                onValueChange = viewModel::onAmountChange,
                label = { Text("Amount", style = typography.body) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = shapes.md,
                colors = textFieldColors
            )

            OutlinedTextField(
                value = uiState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Description", style = typography.body) },
                modifier = Modifier.fillMaxWidth(),
                shape = shapes.md,
                colors = textFieldColors
            )

            var showDatePicker by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = uiState.date.toString(),
                onValueChange = {},
                readOnly = true,
                enabled = false,
                label = { Text("Due Date", style = typography.body) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                shape = shapes.md,
                colors = textFieldColors,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_date_range),
                        contentDescription = null,
                        tint = colors.iconInactive
                    )
                }
            )

            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = uiState.date.toEpochDay() * 86400000
                )

                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val selectedDate = LocalDate.ofEpochDay(millis / 86400000)
                                viewModel.onDateChange(selectedDate)
                            }
                            showDatePicker = false
                        }) { Text("OK", color = colors.primaryAccent) }
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

            uiState.error?.let {
                Text(
                    text = it,
                    style = typography.body,
                    color = colors.error
                )
            }

            val context = LocalContext.current

            Button(
                onClick = {
                    viewModel.save(context) {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = shapes.md,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primaryAccent,
                    contentColor = colors.background
                )
            ) {
                Text(
                    text = "Save",
                    style = typography.headline2
                )
            }
        }
    }
}