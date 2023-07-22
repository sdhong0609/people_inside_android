package com.beside153.peopleinside.model.mycontent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class RatedContentModel(
    @SerialName("content_id") val contentId: Int,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("review") val review: Review?,
    @SerialName("rating") val rating: Rating?
) : Parcelable

@Parcelize
@Serializable
data class Review(
    @SerialName("content_id") val contentId: Int,
    @SerialName("review_id") val reviewId: Int,
    @SerialName("content") val content: String,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("writer") val writer: String?
) : Parcelable

@Parcelize
@Serializable
data class Rating(
    @SerialName("content_id") val contentId: Int,
    @SerialName("rating_id") val ratingId: Int,
    @SerialName("rating") val rating: Float
) : Parcelable
