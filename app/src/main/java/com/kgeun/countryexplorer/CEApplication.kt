package com.kgeun.countryexplorer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CEApplication : Application() {
    companion object {
        @Volatile var instance: CEApplication? = null
            get() {
                return field ?: synchronized(this) {
                    instance ?: CEApplication().also { instance = it }
                }
            }
    }
}