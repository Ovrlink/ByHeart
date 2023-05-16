package com.example.byheart.core

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Change theme for the app
    }

}