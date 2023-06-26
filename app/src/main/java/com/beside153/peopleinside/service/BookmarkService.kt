package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.bookmark.BookmarkToggleResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookmarkService {
    @POST("/api/media-content/{contentId}/bookmark")
    suspend fun postBookmarkStatus(@Path("contentId") contentId: Int): BookmarkToggleResponse

    @GET("/api/media-content/{contentId}/bookmark")
    suspend fun getBookmarkStatus(@Path("contentId") contentId: Int): Boolean
}
