package com.beside153.peopleinside.model.mediacontent.review

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CreateReviewRequest(
    @SerialName("content") val content: String
) : Parcelable
