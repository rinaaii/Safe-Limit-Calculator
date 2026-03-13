package com.example.safelimitcalculator.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import com.example.safelimitcalculator.ui.viewmodel.AddEditExpenseViewModel
import com.example.safelimitcalculator.ui.viewmodel.AddEditPaymentViewModel
import com.example.safelimitcalculator.ui.viewmodel.AnalyticsViewModel
import com.example.safelimitcalculator.ui.viewmodel.ExpensesViewModel
import com.example.safelimitcalculator.ui.viewmodel.HomeViewModel
import com.example.safelimitcalculator.ui.viewmodel.OnboardingViewModel1
import com.example.safelimitcalculator.ui.viewmodel.OnboardingViewModel2
import com.example.safelimitcalculator.ui.viewmodel.PaymentDetailViewModel
import com.example.safelimitcalculator.ui.viewmodel.PaymentsViewModel
import com.example.safelimitcalculator.ui.viewmodel.PreloaderViewModel
import com.example.safelimitcalculator.ui.viewmodel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ExpensesViewModel(get()) }
    viewModel { (savedStateHandle: SavedStateHandle) ->
        AddEditExpenseViewModel(get(), savedStateHandle)
    }
    viewModel { PaymentsViewModel(get()) }
    viewModel { PaymentDetailViewModel(get()) }
    viewModel { AddEditPaymentViewModel(get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { AnalyticsViewModel(get()) }
    viewModel { PreloaderViewModel(get()) }
    viewModel { OnboardingViewModel1() }
    viewModel { OnboardingViewModel2(get()) }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
}