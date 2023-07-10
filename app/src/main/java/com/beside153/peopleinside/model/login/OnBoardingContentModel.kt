package com.beside153.peopleinside.model.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OnBoardingContentModel(
    @SerialName("content_id") val contentId: Int,
    @SerialName("title") val title: String,
    @SerialName("poster_path") val posterPath: String,
    val isChosen: Boolean = false
) : Parcelable
