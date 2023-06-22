package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.search.SearchHotModel
import com.beside153.peopleinside.model.search.SearchedContentModel
import com.beside153.peopleinside.model.search.SearchingTitleModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {
    @GET("/api/media-content/search/title/{keyword}")
    suspend fun getSearchingTitleList(@Path("keyword") keyword: String): List<SearchingTitleModel>

    @GET("/api/media-content/search/{keyword}")
    suspend fun getSearchedContentList(
        @Path("keyword") keyword: String,
        @Query("page") page: Int
    ): List<SearchedContentModel>

    @GET("/api/media-content/hot")
    suspend fun getHotContentList(): List<SearchHotModel>
}
