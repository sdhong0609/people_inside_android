package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.login.AuthRegisterRequest
import com.beside153.peopleinside.model.login.AuthRegisterResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignUpService {
    @POST("/api/auth/register")
    suspend fun postAuthRegister(
        @Header("authorization") authToken: String,
        @Body userInfo: AuthRegisterRequest
    ): AuthRegisterResponse

    @POST("/api/auth/login/kakao")
    suspend fun postLoginKakao(
        @Header("authorization") authToken: String
    ): AuthRegisterResponse
}
