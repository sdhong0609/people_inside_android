package com.beside153.peopleinside.model.mediacontent.rating

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ContentRatingRequest(
    @SerialName("rating") val rating: Float
) : Parcelable
