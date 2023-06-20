package com.beside153.peopleinside.model.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class AuthRegisterRequest(
    val mbti: String,
    val nickName: String,
    val sex: String,
    val birth: String,
    val social: String
) : Parcelable
