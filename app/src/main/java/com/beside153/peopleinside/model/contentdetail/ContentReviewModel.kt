package com.beside153.peopleinside.model.contentdetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ContentReviewModel(
    @SerialName("id") val id: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("content") val content: String,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("writer_id") val writerId: Int,
    @SerialName("content_id") val contentId: Int,
    @SerialName("writer") val writer: Writer
) : Parcelable

@Parcelize
@Serializable
data class Writer(
    @SerialName("id") val id: Int,
    @SerialName("social") val social: String?,
    @SerialName("account") val account: String,
    @SerialName("nickName") val nickName: String,
    @SerialName("mbti") val mbti: String,
    @SerialName("birth") val birth: String,
    @SerialName("sex") val sex: String?,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String?
) : Parcelable
