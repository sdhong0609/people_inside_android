package com.beside153.peopleinside.model.community.comment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CommunityCommentModel(
    @SerialName("comment_id") val commentId: Long,
    @SerialName("content") val content: String,
    @SerialName("author") val author: Author
) : Parcelable

@Parcelize
@Serializable
data class Author(
    @SerialName("user_id") val userId: Long,
    @SerialName("social") val social: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("mbti") val mbti: String,
    @SerialName("birth") val birth: String,
    @SerialName("sex") val sex: String
) : Parcelable
