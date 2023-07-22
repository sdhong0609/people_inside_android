package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.rating.ContentRatingModel
import com.beside153.peopleinside.model.rating.ContentRatingRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RatingService {
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
}
