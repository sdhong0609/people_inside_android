package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.contentdetail.ContentCommentModel
import com.beside153.peopleinside.model.contentdetail.ContentDetailModel
import com.beside153.peopleinside.model.contentdetail.ContentRatingModel
import retrofit2.http.GET
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
}
