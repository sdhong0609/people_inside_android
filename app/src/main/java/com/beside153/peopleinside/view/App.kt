package com.beside153.peopleinside.view

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    }

    companion object {
        private const val PREFS_KEY = "PREFS_KEY"
        lateinit var sharedPreferences: SharedPreferences
    }
}
