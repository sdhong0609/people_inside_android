package com.beside153.peopleinside.model.contentdetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ContentDetailModel(
    @SerialName("content_id") val contentId: Int,
    @SerialName("media_type") val mediaType: String,
    @SerialName("title") val title: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("runtime") val runtime: Int?,
    @SerialName("tagline") val tagline: String,
    @SerialName("genres") val genres: List<Genre>,
    @SerialName("networks") val networks: List<Network>
) : Parcelable

@Parcelize
@Serializable
data class Genre(
    @SerialName("genre_id") val genreId: Int,
    @SerialName("media_type") val mediaType: String,
    @SerialName("name") val name: String
) : Parcelable

@Parcelize
@Serializable
data class Network(
    @SerialName("network_id") val networkId: Int,
    @SerialName("name") val name: String,
    @SerialName("homepage") val homepage: String,
    @SerialName("logo_path") val logoPath: String
) : Parcelable
