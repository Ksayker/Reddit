package com.ksayker.reddit.ui.base

import android.app.Application
import com.ksayker.reddit.ui.di.AppModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        AppModule.initAppModule(this)
    }
}