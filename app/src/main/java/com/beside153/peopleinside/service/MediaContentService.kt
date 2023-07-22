package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.contentdetail.ContentDetailModel
import com.beside153.peopleinside.model.login.OnBoardingChosenContentModel
import com.beside153.peopleinside.model.login.OnBoardingContentModel
import com.beside153.peopleinside.model.recommend.Pick10Model
import com.beside153.peopleinside.model.recommend.RatingBattleModel
import com.beside153.peopleinside.model.recommend.SubRankingModel
import com.beside153.peopleinside.model.search.SearchHotModel
import com.beside153.peopleinside.model.search.SearchedContentModel
import com.beside153.peopleinside.model.search.SearchingTitleModel
import com.beside153.peopleinside.model.search.ViewLogContentModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

@Suppress("TooManyFunctions")
interface MediaContentService {
    @GET("/api/media-content/detail/{contentId}")
    suspend fun getContentDetail(@Path("contentId") contentId: Int): ContentDetailModel

    @GET("/api/media-content/search/title/{keyword}")
    suspend fun getSearchingTitleList(@Path("keyword") keyword: String): List<SearchingTitleModel>

    @GET("/api/media-content/search/{keyword}")
    suspend fun getSearchedContentList(
        @Path("keyword") keyword: String,
        @Query("page") page: Int
    ): List<SearchedContentModel>

    @GET("/api/media-content/recommend/pick")
    suspend fun getPick10List(@Query("page") page: Int): List<Pick10Model>

    @GET("/api/media-content/rating-battle")
    suspend fun getRatingBattleItem(@Query("mediaType") mediaType: String): RatingBattleModel

    @GET("/api/media-content/recommend/my-mbti")
    suspend fun getSubRankingItem(
        @Query("mediaType") mediaType: String,
        @Query("maxTake") maxTake: Int
    ): List<SubRankingModel>

    @GET("/api/media-content/hot")
    suspend fun getHotContentList(): List<SearchHotModel>

    @GET("/api/media-content/view-log")
    suspend fun getViewLogList(): List<ViewLogContentModel>

    @POST("/api/media-content/{contentId}/view-log")
    suspend fun postViewLog(@Path("contentId") contentId: Int, @Query("logType") logType: String): Boolean

    @GET("/api/media-content/onboarding")
    suspend fun getOnBoardingContents(@Query("page") page: Int): List<OnBoardingContentModel>

    @POST("/api/media-content/ratings")
    suspend fun postChosenContents(@Body contenList: List<OnBoardingChosenContentModel>): Boolean
}
