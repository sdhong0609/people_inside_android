package com.beside153.peopleinside.model.community.post

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CommunityPostModel(
    @SerialName("post_id")
    val postId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("total_comment")
    val totalComment: Long,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("mbti_list")
    val mbtiList: List<String>,
    @SerialName("author")
    val author: Author
) : Parcelable

@Parcelize
@Serializable
data class Author(
    @SerialName("user_id")
    val userId: Long,
    @SerialName("social")
    val social: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("mbti")
    val mbti: String,
    @SerialName("birth")
    val birth: String,
    @SerialName("sex")
    val sex: String
) : Parcelable
