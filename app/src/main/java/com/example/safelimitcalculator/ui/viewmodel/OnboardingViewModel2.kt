package com.example.safelimitcalculator.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.safelimitcalculator.ui.navigation.BottomNavItem

class OnboardingViewModel2(
    private val preferences: SharedPreferences
) : ViewModel() {

    fun finishOnboarding(navController: NavController) {
        preferences.edit().putBoolean("is_first_run", false).apply()
        navController.navigate(BottomNavItem.Home.route) {
            popUpTo("onboarding_1") { inclusive = true }
        }
    }
}
