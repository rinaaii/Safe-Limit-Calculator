package com.example.safelimitcalculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.safelimitcalculator.ui.GradientMotionAnimation
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.example.safelimitcalculator.ui.viewmodel.PreloaderViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.size

@Composable
fun PreloaderScreen(
    navController: NavController,
    viewModel: PreloaderViewModel = koinViewModel()
) {
    val colors = LocalAppTheme.colors
    val dimens = LocalAppTheme.dimens
    val typography = LocalAppTheme.typography
    val navigationState by viewModel.navigationState.collectAsState()

    LaunchedEffect(navigationState) {
        navigationState?.let { route ->
            navController.navigate(route) {
                popUpTo("preloader") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        contentAlignment = Alignment.Center
    ) {
        GradientMotionAnimation(
            primaryColor = colors.primaryAccent,
            secondaryColor = colors.secondaryAccent
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimens.md)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = colors.primaryAccent,
                strokeWidth = 3.dp
            )

            Text(
                text = "Initializing...",
                style = typography.body,
                color = colors.background.copy(alpha = 0.6f)
            )
        }
    }
}