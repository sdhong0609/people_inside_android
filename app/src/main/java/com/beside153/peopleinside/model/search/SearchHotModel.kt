package com.beside153.peopleinside.model.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SearchHotModel(
    val rank: Int = 1,
    @SerialName("content_id") val contentId: Int,
    @SerialName("title") val title: String
) : Parcelable
