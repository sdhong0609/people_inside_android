package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.user.UserInfo
import com.beside153.peopleinside.model.editprofile.EdittedUserInfo
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface EditProfileService {
    @PATCH("/api/users/{id}")
    suspend fun patchUserInfo(@Path("id") id: Int, @Body edittedUserInfo: EdittedUserInfo): ApiResponse<UserInfo>
}
