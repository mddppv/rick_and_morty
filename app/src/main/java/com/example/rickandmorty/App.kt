package com.example.rickandmorty

import android.app.Application
import com.example.rickandmorty.core.di.networkModule
import com.example.rickandmorty.core.di.repositoryModule
import com.example.rickandmorty.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}