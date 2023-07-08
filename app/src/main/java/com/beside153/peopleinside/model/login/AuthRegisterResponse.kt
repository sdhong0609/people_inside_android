package com.beside153.peopleinside.model.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class AuthRegisterResponse(
    @SerialName("access_token") val jwtToken: String,
    @SerialName("user") val user: UserInfo
) : Parcelable
