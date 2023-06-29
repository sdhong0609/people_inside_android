package com.beside153.peopleinside.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    val jwtTokenKey = "JWT_TOKEN"
    val userIdKey = "USER_ID"
    val userMbtiKey = "USER_MBTI"
    val userNicknameKey = "USER_NICKNAME"

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String): String {
        return prefs.getString(key, "").toString()
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

    fun getUserId(): Int {
        return prefs.getInt(userIdKey, 0)
    }

    fun getMbti(): String {
        return prefs.getString(userMbtiKey, "").toString()
    }

    fun getNickname(): String {
        return prefs.getString(userNicknameKey, "").toString()
    }
}
