package com.example.safelimitcalculator.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.safelimitcalculator.R
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.example.safelimitcalculator.ui.viewmodel.OnboardingViewModel1
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen1(
    navController: NavController,
    viewModel: OnboardingViewModel1 = koinViewModel()
) {
    val colors = LocalAppTheme.colors
    val typography = LocalAppTheme.typography
    val dimens = LocalAppTheme.dimens
    val shapes = LocalAppTheme.shapes

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(dimens.screenPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_analytics),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = colors.primaryAccent
            )

            Spacer(modifier = Modifier.height(dimens.xl))

            Text(
                text = stringResource(R.string.take_control),
                style = typography.headline1,
                color = colors.textPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimens.md))

            Text(
                text = stringResource(R.string.track_your_daily_expenses_and_prevent_going_into_the_red_we_help_you_stay_within_your_safe_limit),
                style = typography.body,
                color = colors.textSecondary,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = { viewModel.onNextClicked(navController) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter),
            shape = shapes.md,
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primaryAccent.copy(alpha = 0.1f),
                contentColor = colors.primaryAccent
            ),
            border = BorderStroke(1.dp, colors.primaryAccent)
        ) {
            Text(text = stringResource(R.string.next), style = typography.headline2)
        }
    }
}


