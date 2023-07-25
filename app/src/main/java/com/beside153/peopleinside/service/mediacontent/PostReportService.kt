package com.beside153.peopleinside.service.mediacontent

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface PostReportService {
    @POST("/api/media-content/{contentId}/review/{reviewId}/report/{reportId}")
    suspend fun postReport(
        @Path("contentId") contentId: Int,
        @Path("reviewId") reviewId: Int,
        @Path("reportId") reportId: Int
    ): ApiResponse<Boolean>
}
