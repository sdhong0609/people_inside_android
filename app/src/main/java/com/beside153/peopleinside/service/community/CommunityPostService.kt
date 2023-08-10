package com.beside153.peopleinside.service.community

import com.beside153.peopleinside.model.community.post.CommunityPostModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityPostService {
    @GET("/api/community/post")
    suspend fun getPostList(
        @Query("page") page: Int,
        @Query("keyword") keyword: String? = null
    ): List<CommunityPostModel>
}
