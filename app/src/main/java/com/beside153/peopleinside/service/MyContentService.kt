package com.beside153.peopleinside.service

import retrofit2.http.GET

interface MyContentService {
    @GET("/api/my-content/bookmark-count")
    suspend fun getBookmarkCount(): Int

    @GET("/api/my-content/rating-count")
    suspend fun getRatingCount(): Int
}
