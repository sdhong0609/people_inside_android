package com.beside153.peopleinside.model.bookmark

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class BookmarkModel(
    @SerialName("id") val id: Int?,
    @SerialName("created_at") val createdAt: String?,
    @SerialName("user_id") val userId: Int?,
    @SerialName("content_id") val contentId: Int?
) : Parcelable
