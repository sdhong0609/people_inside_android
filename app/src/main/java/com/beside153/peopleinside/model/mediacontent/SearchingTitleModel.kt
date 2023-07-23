package com.beside153.peopleinside.model.mediacontent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SearchingTitleModel(
    @SerialName("content_id") val contentId: Int,
    @SerialName("title") val title: String
) : Parcelable
