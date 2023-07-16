package com.beside153.peopleinside.model.recommend

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class RatingBattleModel(
    @SerialName("content_id") val contentId: Int,
    @SerialName("title") val title: String,
    @SerialName("media_type") val mediaType: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("top_mbti_rating") val topMbtiRating: MbtiRating,
    @SerialName("bottom_mbti_rating") val bottomMbtiRating: MbtiRating
) : Parcelable

@Parcelize
@Serializable
data class MbtiRating(
    @SerialName("mbti") val mbti: String,
    @SerialName("rating_avg") val ratingAvg: Double,
    @SerialName("review_id") val reviewId: Int? = null,
    @SerialName("review_content") val reviewContent: String? = "",
    @SerialName("review_like_count") val reviewLikeCount: Int? = 0
) : Parcelable
