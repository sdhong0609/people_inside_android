package com.beside153.peopleinside.model.login

import android.os.Parcelable
import com.kakao.sdk.user.model.AgeRange
import com.kakao.sdk.user.model.Gender
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoModel(
    val nickname: String? = null,
    val email: String? = null,
    val gender: Gender? = null,
    val ageRange: AgeRange? = null,
    val birthday: String? = null
) : Parcelable
