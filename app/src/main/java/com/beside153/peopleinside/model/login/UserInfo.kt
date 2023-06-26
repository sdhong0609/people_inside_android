package com.beside153.peopleinside.model.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UserInfo(
    @SerialName("id") val id: Int,
    @SerialName("nickName") val nickName: String,
    @SerialName("mbti") val mbti: String,
    @SerialName("birth") val birth: String,
    @SerialName("sex") val sex: String
) : Parcelable
