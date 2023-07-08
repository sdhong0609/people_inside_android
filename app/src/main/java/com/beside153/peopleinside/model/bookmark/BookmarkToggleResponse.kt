package com.beside153.peopleinside.model.bookmark

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class BookmarkToggleResponse(
    @SerialName("toggle_status") val toggleStatus: String
) : Parcelable
