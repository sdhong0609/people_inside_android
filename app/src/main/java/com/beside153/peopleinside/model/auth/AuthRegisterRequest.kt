package com.beside153.peopleinside.model.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class AuthRegisterRequest(
    val social: String,
    val nickname: String,
    val mbti: String,
    val birth: String,
    val sex: String
) : Parcelable
