package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.login.OnBoardingChosenContentModel
import com.beside153.peopleinside.model.login.OnBoardingCompletedResponse
import com.beside153.peopleinside.model.login.OnBoardingContentModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OnBoardingService {
    @GET("/api/media-content/onboarding")
    suspend fun getOnBoardkingContents(@Query("page") page: Int): List<OnBoardingContentModel>

    @POST("/api/media-content/ratings")
    suspend fun postChosenContents(@Body contenList: List<OnBoardingChosenContentModel>): Boolean

    @POST("/api/users/{id}/onboarding-completed")
    suspend fun postOnBoardingCompleted(@Path("id") id: Int): OnBoardingCompletedResponse

    @GET("/api/users/{id}/onboarding-completed")
    suspend fun getOnBoardingCompleted(@Path("id") id: Int): Boolean
}
