package com.ksma.moviedb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieDBApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}