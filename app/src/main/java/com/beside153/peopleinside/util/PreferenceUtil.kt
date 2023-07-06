package com.beside153.peopleinside.util

import android.content.Context
import android.content.SharedPreferences

@Suppress("TooManyFunctions")
class PreferenceUtil(context: Context) {
    val jwtTokenKey = "JWT_TOKEN"
    private val userIdKey = "USER_ID"
    private val userMbtiKey = "USER_MBTI"
    private val userNicknameKey = "USER_NICKNAME"
    private val userGenderKey = "USER_GENDER"
    private val userBirthKey = "USER_BIRTH"
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

    fun setUserId(i: Int) {
        prefs.edit().putInt(userIdKey, i).apply()
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

    fun getGender(): String {
        return prefs.getString(userGenderKey, "").toString()
    }

    fun setGender(str: String) {
        prefs.edit().putString(userGenderKey, str).apply()
    }

    fun getBirth(): String {
        return prefs.getString(userBirthKey, "").toString()
    }

    fun setBirth(str: String) {
        prefs.edit().putString(userBirthKey, str).apply()
    }

    fun getEmail(): String {
        return prefs.getString("email", "").toString()
    }

    fun setEmail(str: String) {
        prefs.edit().putString("email", str).apply()
    }
}
