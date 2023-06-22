package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.recommend.Pick10Model
import retrofit2.http.GET

interface RecommendService {
    @GET("/api/media-content/recommend/pick")
    suspend fun getPick10List(): List<Pick10Model>
}
