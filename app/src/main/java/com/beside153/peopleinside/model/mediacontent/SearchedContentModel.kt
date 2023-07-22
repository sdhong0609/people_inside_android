package com.beside153.peopleinside.model.mediacontent

import android.os.Parcelable
import com.beside153.peopleinside.model.mediacontent.review.Writer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SearchedContentModel(
    @SerialName("content_id") val contentId: Int,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("total_rating") val totalRating: Double? = 0.0,
    @SerialName("mbti_rating") val mbtiRating: Double? = 0.0,
    @SerialName("bookmarked") val bookmarked: Boolean,
    @SerialName("top_like_review") val topLikeReview: TopLikeReview? = null
) : Parcelable

@Parcelize
@Serializable
data class TopLikeReview(
    @SerialName("content_id") val contentId: Int,
    @SerialName("review_id") val reviewId: Int,
    @SerialName("content") val content: String,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("writer") val writer: Writer
) : Parcelable
