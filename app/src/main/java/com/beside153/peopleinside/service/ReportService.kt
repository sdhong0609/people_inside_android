package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.report.ReportModel
import retrofit2.http.GET

interface ReportService {
    @GET("/api/report")
    suspend fun getReportList(): List<ReportModel>
}
