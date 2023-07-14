package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.login.UserInfo
import com.beside153.peopleinside.model.report.ResonIdModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path

interface UserService {
    @GET("/api/users/{id}")
    suspend fun getUserInfo(@Path("id") id: Int): UserInfo

    @HTTP(method = "DELETE", path = "/api/users/{userId}", hasBody = true)
    suspend fun deleteUser(@Path("userId") id: Int, @Body reasonId: ResonIdModel): Boolean
}
