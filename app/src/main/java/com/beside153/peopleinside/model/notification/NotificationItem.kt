package com.beside153.peopleinside.model.notification

data class NotificationItem(
    val id: Int,
    val emoji: String,
    val title: String,
    val description: String,
    val time: String
)
