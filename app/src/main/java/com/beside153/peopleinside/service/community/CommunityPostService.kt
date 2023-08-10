package com.beside153.peopleinside.service.community

import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.model.community.post.CommunityPostRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CommunityPostService {
    @GET("/api/community/post")
    suspend fun getCommunityPostList(
        @Query("page") page: Int,
        @Query("keyword") keyword: String? = null
    ): List<CommunityPostModel>

    @POST("/api/community/post")
    suspend fun postCommunityPost(@Body communityPostRequest: CommunityPostRequest): CommunityPostModel
}
