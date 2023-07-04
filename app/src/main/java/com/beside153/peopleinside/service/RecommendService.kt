package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.contentdetail.ContentReviewModel
import com.beside153.peopleinside.model.contentdetail.CreateReviewRequest
import com.beside153.peopleinside.model.recommend.Pick10Model
import com.beside153.peopleinside.model.recommend.RatingBattleModel
import com.beside153.peopleinside.model.recommend.SubRankingModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RecommendService {
    @GET("/api/media-content/recommend/pick")
    suspend fun getPick10List(@Query("page") page: Int): List<Pick10Model>

    @GET("/api/media-content/rating-battle")
    suspend fun getRatingBattleItem(@Query("mediaType") mediaType: String): RatingBattleModel

    @GET("/api/media-content/recommend/my-mbti")
    suspend fun getSubRankingItem(
        @Query("mediaType") mediaType: String,
        @Query("maxTake") maxTake: Int
    ): List<SubRankingModel>

    @POST("/api/media-content/{contentId}/review")
    suspend fun postReview(@Path("contentId") contentId: Int, @Body content: CreateReviewRequest): ContentReviewModel

    @PUT("/api/media-content/{contentId}/review")
    suspend fun putReview(@Path("contentId") contentId: Int, @Body content: CreateReviewRequest): ContentReviewModel

    @DELETE("/api/media-content/{contentId}/review/{reviewId}")
    suspend fun deleteReview(@Path("contentId") content: Int, @Path("reviewId") reviewId: Int): Boolean
}
