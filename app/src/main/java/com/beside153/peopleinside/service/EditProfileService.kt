package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.login.UserInfo
import com.beside153.peopleinside.model.mypage.EdittedUserInfo
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface EditProfileService {
    @PATCH("/api/users/{id}")
    suspend fun patchUserInfo(@Path("id") id: Int, @Body edittedUserInfo: EdittedUserInfo): ApiResponse<UserInfo>
}
