package com.beside153.peopleinside.model.withdrawal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class WithDrawalReasonModel(
    @SerialName("reason_id") val reasonId: Int,
    @SerialName("name") val title: String
) : Parcelable
