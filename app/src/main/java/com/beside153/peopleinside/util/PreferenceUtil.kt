package com.beside153.peopleinside.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    val jwtTokenKey = "JWT_TOKEN"
    val userIdKey = "USER_ID"

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun getInt(key: String): Int {
        return prefs.getInt(key, 0)
    }

    fun setInt(key: String, i: Int) {
        prefs.edit().putInt(key, i).apply()
    }
}
