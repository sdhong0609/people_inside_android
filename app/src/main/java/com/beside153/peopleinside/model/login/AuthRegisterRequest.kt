package com.beside153.peopleinside.model.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthRegisterRequest(
    val mbti: String,
    val nickName: String,
    val sex: String,
    val birth: String,
    val social: String
) : Parcelable
