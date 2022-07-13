package com.repo01.repoapp.data.model

data class SearchItemModel(
    val repoName: String,
    val ownerName: String,
    val avatarUrl: String?,
    val description: String?,
    val stargazers_count: String,
    val language: String?
)