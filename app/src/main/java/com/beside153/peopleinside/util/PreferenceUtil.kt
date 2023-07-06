package com.beside153.peopleinside.util

import android.content.Context
import android.content.SharedPreferences

@Suppress("TooManyFunctions")
class PreferenceUtil(context: Context) {
    val jwtTokenKey = "JWT_TOKEN"
    val userIdKey = "USER_ID"
    private val userMbtiKey = "USER_MBTI"
    private val userNicknameKey = "USER_NICKNAME"
    val reportListKey = "REPORT_LIST"

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
        return prefs.getString(userMbtiKey, "").toString().uppercase()
    }

    fun setMbti(str: String) {
        prefs.edit().putString(userMbtiKey, str).apply()
    }

    fun getNickname(): String {
        return prefs.getString(userNicknameKey, "").toString()
    }

    fun setNickname(str: String) {
        prefs.edit().putString(userNicknameKey, str).apply()
    }

    fun getEmail(): String {
        return prefs.getString("email", "").toString()
    }

    fun setEmail(str: String) {
        prefs.edit().putString("email", str).apply()
    }
}
