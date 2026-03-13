package com.example.safelimitcalculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.safelimitcalculator.R
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.example.safelimitcalculator.ui.viewmodel.AddEditExpenseViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseScreen(
    navController: NavController,
    viewModel: AddEditExpenseViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val colors = LocalAppTheme.colors
    val typography = LocalAppTheme.typography
    val dimens = LocalAppTheme.dimens
    val shapes = LocalAppTheme.shapes
    val textFieldColors = LocalAppTheme.textFieldColors

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
                text = if (uiState.isEditMode) stringResource(R.string.edit_expense) else stringResource(
                    R.string.add_expense
                ),
                style = typography.headline1,
                color = colors.textPrimary
            )

            OutlinedTextField(
                value = uiState.amount,
                onValueChange = viewModel::onAmountChange,
                label = { Text(stringResource(R.string.amount), style = typography.body) },
                isError = uiState.error != null,
                modifier = Modifier.fillMaxWidth(),
                shape = shapes.md,
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = uiState.category,
                onValueChange = viewModel::onCategoryChange,
                label = { Text(stringResource(R.string.category), style = typography.body) },
                modifier = Modifier.fillMaxWidth(),
                shape = shapes.md,
                colors = textFieldColors
            )

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
                    text = if (uiState.isEditMode) stringResource(R.string.update) else stringResource(
                        R.string.save
                    ),
                    style = typography.headline2
                )
            }
        }
    }
}
