package com.beside153.peopleinside.service.community

import com.beside153.peopleinside.model.common.CreateContentRequest
import com.beside153.peopleinside.model.community.comment.CommunityCommentModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityCommentService {
    @GET("/api/community/post/{postId}/comment")
    suspend fun getCommunityCommentList(
        @Path("postId") postId: Long,
        @Query("page") page: Int
    ): List<CommunityCommentModel>

    @POST("/api/community/post/{postId}/comment")
    suspend fun postCommunityComment(
        @Path("postId") postId: Long,
        @Body content: CreateContentRequest
    ): CommunityCommentModel

    @PATCH("/api/community/post/{postId}/comment/{commentId}")
    suspend fun patchCommunityComment(
        @Path("postId") postId: Long,
        @Path("commentId") commentId: Long,
        @Body content: CreateContentRequest
    ): CommunityCommentModel

    @DELETE("/api/community/post/{postId}/comment/{commentId}")
    suspend fun deleteCommunityComment(
        @Path("postId") postId: Long,
        @Path("commentId") commentId: Long
    ): Boolean

    @POST("/api/community/post/{postId}/comment/{commentId}/report")
    suspend fun postCommunityCommentReport(
        @Path("postId") postId: Long,
        @Path("commentId") commentId: Long,
        @Query("reportId") reportId: Int
    ): Boolean
}
