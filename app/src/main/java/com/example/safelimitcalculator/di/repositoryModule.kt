package com.example.safelimitcalculator.di

import com.example.safelimitcalculator.data.repository.ExpenseRepository
import com.example.safelimitcalculator.data.repository.PaymentRepository
import com.example.safelimitcalculator.data.repository.SettingsRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { ExpenseRepository(get()) }
    single { PaymentRepository(get()) }
    single { SettingsRepository(get()) }
}