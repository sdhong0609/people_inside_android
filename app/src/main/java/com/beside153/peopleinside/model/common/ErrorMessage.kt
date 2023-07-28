package com.beside153.peopleinside.model.common

import androidx.annotation.StringRes

data class ErrorMessage(
    val message: String? = null,
    @StringRes val messageRes: Int? = null
)
