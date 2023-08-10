package com.beside153.peopleinside.model.community.post

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CommunityPostRequest(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("mbti")
    val mbti: Mbti
) : Parcelable

@Parcelize
@Serializable
data class Mbti(
    val enfj: Boolean? = false,
    val enfp: Boolean? = false,
    val entj: Boolean? = false,
    val entp: Boolean? = false,
    val esfj: Boolean? = false,
    val esfp: Boolean? = false,
    val estj: Boolean? = false,
    val estp: Boolean? = false,

    val infj: Boolean? = false,
    val infp: Boolean? = false,
    val intj: Boolean? = false,
    val intp: Boolean? = false,
    val isfj: Boolean? = false,
    val isfp: Boolean? = false,
    val istj: Boolean? = false,
    val istp: Boolean? = false
) : Parcelable
