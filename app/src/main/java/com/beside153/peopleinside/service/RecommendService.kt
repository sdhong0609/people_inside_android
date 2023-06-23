package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.recommend.Pick10Model
import com.beside153.peopleinside.model.recommend.RatingBattleModel
import com.beside153.peopleinside.model.recommend.SubRankingModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RecommendService {
    @GET("/api/media-content/recommend/pick")
    suspend fun getPick10List(): List<Pick10Model>

    @GET("/api/media-content/rating-battle")
    suspend fun getRatingBattleItem(@Query("mediaType") mediaType: String): RatingBattleModel

    @GET("/api/media-content/recommend/my-mbti")
    suspend fun getSubRankingItem(
        @Query("mediaType") mediaType: String,
        @Query("maxTake") maxTake: Int
    ): List<SubRankingModel>
}
