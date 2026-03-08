package com.example.safelimitcalculator.di

import androidx.room.Room
import com.example.safelimitcalculator.data.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    single { get<AppDatabase>().expenseDao() }
    single { get<AppDatabase>().paymentDao() }
    single { get<AppDatabase>().settingsDao() }
}