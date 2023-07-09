package com.beside153.peopleinside.service

import com.beside153.peopleinside.model.common.ErrorEnvelope
import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message

object ErrorEnvelopeMapper : ApiErrorModelMapper<ErrorEnvelope> {

    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): ErrorEnvelope {
        return ErrorEnvelope(
            apiErrorResponse.response.isSuccessful,
            apiErrorResponse.statusCode.code,
            apiErrorResponse.message()
        )
    }
}
