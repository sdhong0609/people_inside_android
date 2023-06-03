package com.beside153.peopleinside.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Top10Item(
    @SerialName("content_id")
    val contentId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("total_rating")
    val totalRating: Float,
    @SerialName("mbti_rating")
    val mbtiRating: Float,
    @SerialName("bookmarked")
    val bookmarked: Boolean
//    @SerialName("top_like_review")
//    val topLikeReview: TopLikeReview
) : Parcelable

@Parcelize
@Serializable
data class TopLikeReview(
    @SerialName("id")
    val id: Int,
    @SerialName("content")
    val content: String,
    @SerialName("like_count")
    val likeCount: Int,
    @SerialName("writer_id")
    val writerId: Int,
    @SerialName("content_id")
    val contentId: Int,
    @SerialName("created_at")
    val createdAt: String
) : Parcelable
