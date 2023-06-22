package com.beside153.peopleinside.model.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SearchingTitleModel(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String
) : Parcelable
