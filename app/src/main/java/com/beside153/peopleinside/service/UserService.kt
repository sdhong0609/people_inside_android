package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.login.UserInfo
import com.beside153.peopleinside.model.mypage.EdittedUserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserService {
    @GET("/api/users/{id}")
    suspend fun getUserInfo(@Path("id") id: Int): UserInfo

    @PATCH("/api/users/{id}")
    suspend fun patchUserInfo(@Path("id") id: Int, @Body edittedUserInfo: EdittedUserInfo): UserInfo
}
