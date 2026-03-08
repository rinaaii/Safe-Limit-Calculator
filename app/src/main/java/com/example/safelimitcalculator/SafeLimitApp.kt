package com.example.safelimitcalculator

import android.app.Application
import com.example.safelimitcalculator.di.databaseModule
import com.example.safelimitcalculator.di.repositoryModule
import com.example.safelimitcalculator.di.viewModelModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class SafeLimitApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin {
            androidContext(this@SafeLimitApp)
            modules(
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}