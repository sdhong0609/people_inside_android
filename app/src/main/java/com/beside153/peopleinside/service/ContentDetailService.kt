package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.contentdetail.ContentCommentModel
import com.beside153.peopleinside.model.contentdetail.ContentDetailModel
import com.beside153.peopleinside.model.contentdetail.ContentRatingModel
import com.beside153.peopleinside.model.contentdetail.ContentRatingRequest
import com.beside153.peopleinside.model.contentdetail.ContentReviewModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ContentDetailService {
    @GET("/api/media-content/detail/{contentId}")
    suspend fun getContentDetail(@Path("contentId") contentId: Int): ContentDetailModel

    @GET("/api/media-content/{contentId}/review")
    suspend fun getContentReviewList(
        @Path("contentId") contentId: Int,
        @Query("page") page: Int
    ): List<ContentCommentModel>

    @GET("/api/media-content/{contentId}/rating/rater/{raterId}")
    suspend fun getContentRating(@Path("contentId") contentId: Int, @Path("raterId") raterId: Int): ContentRatingModel

    @POST("/api/media-content/{contentId}/rating")
    suspend fun postContentRating(
        @Path("contentId") contentId: Int,
        @Body contentRatingRequest: ContentRatingRequest
    ): ContentRatingModel

    @PUT("/api/media-content/{contentId}/rating")
    suspend fun putContentRating(
        @Path("contentId") contentId: Int,
        @Body contentRatingRequest: ContentRatingRequest
    ): ContentRatingModel

    @DELETE("/api/media-content/{contentId}/rating/{ratingId}")
    suspend fun deleteContentRating(@Path("contentId") contentId: Int, @Path("ratingId") ratingId: Int): Boolean

    @GET("/api/media-content/{contentId}/review/writer/{writerId}")
    suspend fun getWriterReview(
        @Path("contentId") contentId: Int,
        @Path("writerId") writerId: Int
    ): ContentReviewModel
}
