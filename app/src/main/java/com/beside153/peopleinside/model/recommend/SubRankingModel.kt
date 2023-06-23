package com.beside153.peopleinside.model.recommend

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SubRankingModel(
    val rank: Int = 1,
    @SerialName("content_id") val contentId: Int,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String,
    @SerialName("total_rating") val totalRating: Double,
    @SerialName("mbti_rating") val mbtiRating: Double
) : Parcelable
