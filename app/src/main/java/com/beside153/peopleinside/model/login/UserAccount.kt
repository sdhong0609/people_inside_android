package com.beside153.peopleinside.model.login

data class UserAccount(
    val nickname: String,
    val email: String? = null,
    val gender: String? = null,
    val ageRange: String? = null,
    val birthday: String? = null
)
