package com.example.safelimitcalculator.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safelimitcalculator.ui.navigation.BottomNavItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow

class PreloaderViewModel(
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _navigationState = MutableStateFlow<String?>(null)
    val navigationState = _navigationState.asStateFlow()

    init {
        checkAppStatus()
    }

    private fun checkAppStatus() {
        viewModelScope.launch {
            delay(1500)

            val isFirstRun = preferences.getBoolean("is_first_run", true)

            if (isFirstRun) {
                _navigationState.value = "onboarding_1"
            } else {
                _navigationState.value = BottomNavItem.Home.route
            }
        }
    }
}
