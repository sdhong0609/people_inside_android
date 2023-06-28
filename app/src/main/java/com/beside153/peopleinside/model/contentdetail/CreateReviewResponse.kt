package com.beside153.peopleinside.model.contentdetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CreateReviewResponse(
    @SerialName("content_id") val contentId: Int,
    @SerialName("review_id") val reviewId: Int,
    @SerialName("content") val content: String,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("writer") val writer: CreatedReviewWriter?
) : Parcelable

@Parcelize
@Serializable
data class CreatedReviewWriter(
    @SerialName("id") val id: Int,
    @SerialName("nickName") val nickName: String,
    @SerialName("mbti") val mbti: String,
    @SerialName("birth") val birth: String,
    @SerialName("sex") val sex: String
) : Parcelable
