package com.beside153.peopleinside.model.editprofile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class EdittedUserInfo(
    @SerialName("nickname") val nickname: String,
    @SerialName("mbti") val mbti: String,
    @SerialName("birth") val birth: String,
    @SerialName("sex") val sex: String
) : Parcelable
