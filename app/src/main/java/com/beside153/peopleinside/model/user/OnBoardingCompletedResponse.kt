package com.beside153.peopleinside.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OnBoardingCompletedResponse(
    @SerialName("id") val id: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("birth") val birth: String,
    @SerialName("mbti") val mbti: String,
    @SerialName("sex") val sex: String,
    @SerialName("social") val social: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("social_id") val socialId: String,
    @SerialName("onboarding_completed") val onboardingCompleted: Boolean
) : Parcelable
