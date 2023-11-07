package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.appversion.AppVersionLatestResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AppVersionService {
    @GET("/api/app-version/{agent}/latest")
    suspend fun getAppVersionLatest(@Path("agent") agent: String): AppVersionLatestResponse
}
