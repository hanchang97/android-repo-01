package com.repo01.repoapp.data.network.response.search

data class SearchResponse(
    val items: List<SearchItemEntity>
)

data class SearchItemEntity(
    val name: String,
    val owner: OwnerEntity,
    val description: String?,
    val stargazers_count: Int,
    val language: String?
)

data class OwnerEntity(
    val login: String,
    val avatar_url: String?
)