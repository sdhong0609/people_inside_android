package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.user.OnBoardingCompletedResponse
import com.beside153.peopleinside.model.user.UserInfo
import com.beside153.peopleinside.model.user.ResonIdModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("/api/users/{id}")
    suspend fun getUserInfo(@Path("id") id: Int): UserInfo

//    @PATCH("/api/users/{id}")
//    suspend fun patchUserInfo(@Path("id") id: Int, @Body edittedUserInfo: EdittedUserInfo): ApiResponse<UserInfo>

    @HTTP(method = "DELETE", path = "/api/users/{userId}", hasBody = true)
    suspend fun deleteUser(@Path("userId") id: Int, @Body reasonId: ResonIdModel): Boolean

    @GET("/api/users/{id}/onboarding-completed")
    suspend fun getOnBoardingCompleted(@Path("id") id: Int): Boolean

    @POST("/api/users/{id}/onboarding-completed")
    suspend fun postOnBoardingCompleted(@Path("id") id: Int): OnBoardingCompletedResponse
}
