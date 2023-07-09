package com.beside153.peopleinside.model.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ErrorEnvelope(
    @SerialName("result") val result: Boolean,
    @SerialName("statusCode") val statusCode: Int,
    @SerialName("message") val message: String
) : Parcelable
