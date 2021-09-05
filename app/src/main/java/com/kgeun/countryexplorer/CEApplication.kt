package com.kgeun.countryexplorer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CEApplication : Application() {
    companion object {
        lateinit var instance: CEApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this@CEApplication
    }
}