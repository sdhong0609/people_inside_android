package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.search.SearchingTitleModel
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchService {
    @GET("/api/media-content/search/title/{keyword}")
    suspend fun getSearchedTitleList(@Path("keyword") keyword: String): List<SearchingTitleModel>
}
