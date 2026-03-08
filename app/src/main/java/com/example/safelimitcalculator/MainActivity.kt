package com.example.safelimitcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.safelimitcalculator.notifications.NotificationHelper
import com.example.safelimitcalculator.notifications.NotificationPermission
import com.example.safelimitcalculator.ui.theme.SafeLimitCalculatorTheme
import com.example.safelimitcalculator.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        NotificationPermission.request(this)
        NotificationHelper.createChannel(this)
        setContent {
            SafeLimitCalculatorTheme {
                MainScreen()
            }
        }
    }
}

