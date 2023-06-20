package com.beside153.peopleinside.service

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitClient {
    private const val baseUrl = "http://27.96.131.197/api/"
    private const val contentType = "application/json"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(contentType.toMediaType()))
        .build()

    val signUpService: SignUpService = retrofit.create(SignUpService::class.java)
    val mbtiService: MbtiService = retrofit.create(MbtiService::class.java)
}
