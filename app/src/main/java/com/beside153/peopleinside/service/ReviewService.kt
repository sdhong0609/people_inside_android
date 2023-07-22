package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.review.ContentCommentModel
import com.beside153.peopleinside.model.review.ContentReviewModel
import com.beside153.peopleinside.model.review.CreateReviewRequest
import com.beside153.peopleinside.model.review.LikeToggleResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {
    @GET("/api/media-content/{contentId}/review/writer/{writerId}")
    suspend fun getWriterReview(
        @Path("contentId") contentId: Int,
        @Path("writerId") writerId: Int
    ): ContentReviewModel

    @GET("/api/media-content/{contentId}/review")
    suspend fun getContentReviewList(
        @Path("contentId") contentId: Int,
        @Query("page") page: Int
    ): List<ContentCommentModel>

    @POST("/api/media-content/{contentId}/review")
    suspend fun postReview(@Path("contentId") contentId: Int, @Body content: CreateReviewRequest): ContentReviewModel

    @PUT("/api/media-content/{contentId}/review")
    suspend fun putReview(@Path("contentId") contentId: Int, @Body content: CreateReviewRequest): ContentReviewModel

    @DELETE("/api/media-content/{contentId}/review/{reviewId}")
    suspend fun deleteReview(@Path("contentId") content: Int, @Path("reviewId") reviewId: Int): Boolean

    @POST("/api/media-content/{contentId}/review/{reviewId}/like")
    suspend fun postLikeToggle(@Path("contentId") contentId: Int, @Path("reviewId") reviewId: Int): LikeToggleResponse

    @POST("/api/media-content/{contentId}/review/{reviewId}/report/{reportId}")
    suspend fun postReport(
        @Path("contentId") contentId: Int,
        @Path("reviewId") reviewId: Int,
        @Path("reportId") reportId: Int
    ): ApiResponse<Boolean>
}
