package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.login.OnBoardingContentModel
import retrofit2.http.GET
import retrofit2.http.Query

interface OnBoardingService {
    @GET("/api/media-content/onboarding")
    suspend fun getOnBoardkingContents(@Query("page") page: Int): List<OnBoardingContentModel>
}
