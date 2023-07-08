package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.liketoggle.LikeToggleResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeToggleService {
    @POST("/api/media-content/{contentId}/review/{reviewId}/like")
    suspend fun postLikeToggle(@Path("contentId") contentId: Int, @Path("reviewId") reviewId: Int): LikeToggleResponse
}
