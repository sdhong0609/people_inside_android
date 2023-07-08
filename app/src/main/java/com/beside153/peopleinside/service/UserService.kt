package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.login.UserInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/api/users/{id}")
    suspend fun getUserInfo(@Path("id") id: Int): UserInfo
}
