package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.report.ReportModel
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportService {
    @GET("/api/report")
    suspend fun getReportList(): List<ReportModel>

    @POST("/api/media-content/{contentId}/review/{reviewId}/report/{reportId}")
    suspend fun postReport(
        @Path("contentId") contentId: Int,
        @Path("reviewId") reviewId: Int,
        @Path("reportId") reportId: Int
    ): ApiResponse<Boolean>
}
