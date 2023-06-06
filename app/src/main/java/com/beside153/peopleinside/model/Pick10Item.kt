package com.beside153.peopleinside.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Pick10Item(
    @SerialName("content_id") val contentId: Int,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("total_rating") val totalRating: Double,
    @SerialName("mbti_rating") val mbtiRating: Double,
    @SerialName("bookmarked") val bookmarked: Boolean,
    @SerialName("top_like_review") val topLikeReview: Review? = null
) : Parcelable

@Parcelize
@Serializable
data class Review(
    @SerialName("id") val id: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("content") val content: String,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("writer_id") val writerId: Int,
    @SerialName("content_id") val contentId: Int,
    @SerialName("Writer") val writer: Writer
) : Parcelable

@Parcelize
@Serializable
data class Writer(
    @SerialName("id") val id: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("account") val account: String,
    @SerialName("password") val password: String,
    @SerialName("name") val name: String,
    @SerialName("role") val role: String
) : Parcelable
