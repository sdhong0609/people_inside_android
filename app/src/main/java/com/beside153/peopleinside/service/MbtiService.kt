package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.Pick10Item
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MbtiService {
    @GET("media-content/{mbti}/popular")
    fun getTop10Content(@Path("mbti") mbti: String): Call<List<Pick10Item>>
}
