package com.repo01.repoapp.data.model

data class IssueItemModel(
    val id: Int,
    val state: String,
    val createdAt: String,
    val title: String,
    val number: Int,
    val fullName: String
)
