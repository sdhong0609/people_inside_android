package com.beside153.peopleinside.model.contentdetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ContentRatingModel(
    @SerialName("content_id") val contentId: Int,
    @SerialName("rating_id") val ratingId: Int,
    @SerialName("rating") val rating: Float
) : Parcelable
