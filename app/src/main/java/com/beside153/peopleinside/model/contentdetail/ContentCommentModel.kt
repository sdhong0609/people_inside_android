package com.beside153.peopleinside.model.contentdetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ContentCommentModel(
    @SerialName("content_id") val contentId: Int,
    @SerialName("review_id") val reviewId: Int,
    @SerialName("content") val content: String,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("writer") val writer: Writer,
    @SerialName("like") val like: Boolean
) : Parcelable

@Parcelize
@Serializable
data class Writer(
    @SerialName("user_id") val userId: Int,
    @SerialName("nickname") val nickname: String,
    @SerialName("mbti") val mbti: String,
    @SerialName("birth") val birth: String,
    @SerialName("sex") val sex: String?
) : Parcelable
