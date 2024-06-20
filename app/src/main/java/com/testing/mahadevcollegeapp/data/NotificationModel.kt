package com.testing.mahadevcollegeapp.data


data class NotificationModel(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
