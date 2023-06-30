package com.beside153.peopleinside.service

import retrofit2.http.POST
import retrofit2.http.Path

interface ReportService {
    @POST("/api/media-content/{contentId}/review/{reviewId}/report/{reportId}")
    suspend fun postReport(
        @Path("contentId") contentId: Int,
        @Path("reviewId") reviewId: Int,
        @Path("reportId") reportId: Int
    ): Boolean
}
