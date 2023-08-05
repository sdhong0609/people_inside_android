package com.beside153.peopleinside.model.appversion

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class AppVersionLatestResponse(
    @SerialName("agent") val agent: String,
    @SerialName("latest_version_name") val latestVersionName: String,
    @SerialName("required_version_name") val requiredVersionName: String
) : Parcelable
