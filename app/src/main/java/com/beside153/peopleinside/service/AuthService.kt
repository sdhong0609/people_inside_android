package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.auth.AuthRegisterRequest
import com.beside153.peopleinside.model.auth.AuthRegisterResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/api/auth/register")
    suspend fun postAuthRegister(
        @Header("authorization") authToken: String,
        @Body userInfo: AuthRegisterRequest
    ): ApiResponse<AuthRegisterResponse>

    @POST("/api/auth/login/kakao")
    suspend fun postLoginKakao(@Header("authorization") authToken: String): AuthRegisterResponse
}
