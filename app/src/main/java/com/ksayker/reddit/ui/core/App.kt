package com.ksayker.reddit.ui.core

import android.app.Application
import com.ksayker.reddit.ui.di.AppModule

class App: Application() {

    override fun onCreate() {
        instance = this

        AppModule.initAppModule(this)

        super.onCreate()
    }

    companion object {
        private lateinit var instance: App

        fun getApp() = instance
    }
}