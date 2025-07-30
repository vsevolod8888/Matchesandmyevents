package com.betskmprandomkmp.app

import android.app.Application
import di.appModule
import di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MatchApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MatchApp)
            modules(appModule, platformModule())
        }
    }
}