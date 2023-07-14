package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.withdrawal.WithDrawalReasonModel
import retrofit2.http.GET

interface WithDrawalService {
    @GET("/api/with-drawal/reason")
    suspend fun getWithDrawalReasonList(): List<WithDrawalReasonModel>
}
