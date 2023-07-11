package com.beside153.peopleinside.model.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OnBoardingChosenContentModel(
    @SerialName("content_id") val contentId: Int,
    @SerialName("rating") val rating: Float
) : Parcelable
