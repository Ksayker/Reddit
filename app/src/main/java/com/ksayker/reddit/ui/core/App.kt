package com.ksayker.reddit.ui.core

import android.app.Application
import com.ksayker.reddit.ui.di.AppModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        AppModule.initAppModule(this)
    }
}