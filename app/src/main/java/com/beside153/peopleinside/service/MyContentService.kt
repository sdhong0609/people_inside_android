package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.mypage.BookmarkedContentModel
import com.beside153.peopleinside.model.mypage.RatingContentModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MyContentService {
    @GET("/api/my-content/bookmark-count")
    suspend fun getBookmarkedCount(): Int

    @GET("/api/my-content/bookmark/{page}")
    suspend fun getBookmarkedContents(@Path("page") page: Int): List<BookmarkedContentModel>

    @GET("/api/my-content/rating-count")
    suspend fun getRatedCount(): Int

    @GET("/api/my-content/rating/{page}")
    suspend fun getRatedContents(@Path("page") page: Int): List<RatingContentModel>
}
