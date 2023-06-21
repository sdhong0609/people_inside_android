package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.contentdetail.ContentDetailModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ContentDetailService {
    @GET("media-content/detail/{contentId}")
    suspend fun getContentDetail(@Path("contentId") contentId: Int): ContentDetailModel
}
