package com.repo01.repoapp.data.model

data class NotificationsItemModel(
    val id: String,
    val updatedAt: String,
    val title: String = "",
    val fullName: String = "",
    var commentNum: Int = 0,
    var orgImageUrl: String = ""
)
