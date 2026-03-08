package com.example.safelimitcalculator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class OnboardingViewModel1 : ViewModel() {
    fun onNextClicked(navController: NavController) {
        navController.navigate("onboarding_2")
    }
}
