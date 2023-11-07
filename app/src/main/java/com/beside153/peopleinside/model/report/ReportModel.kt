package com.beside153.peopleinside.model.report

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ReportModel(
    @SerialName("id") val id: Int,
    @SerialName("type") val type: String,
    @SerialName("content") val content: String
) : Parcelable
