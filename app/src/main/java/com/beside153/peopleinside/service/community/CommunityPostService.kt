package com.beside153.peopleinside.service.community

import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.model.community.post.CommunityPostRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityPostService {
    @GET("/api/community/post")
    suspend fun getCommunityPostList(
        @Query("page") page: Int,
        @Query("keyword") keyword: String? = null
    ): List<CommunityPostModel>

    @POST("/api/community/post")
    suspend fun postCommunityPost(@Body communityPostRequest: CommunityPostRequest): CommunityPostModel

    @GET("/api/community/post/{postId}")
    suspend fun getCommunityPostDetail(@Path("postId") postId: Long): CommunityPostModel

    @PATCH("/api/community/post/{postId}")
    suspend fun patchCommunityPost(
        @Path("postId") postId: Long,
        @Body communityPostRequest: CommunityPostRequest
    ): CommunityPostModel

    @DELETE("/api/community/post/{postId}")
    suspend fun deleteCommunityPost(@Path("postId") postId: Long): Boolean

    @POST("/api/community/post/{postId}/report")
    suspend fun postCommunityPostReport(@Path("postId") postId: Long, @Query("reportId") reportId: Int): Boolean
}
