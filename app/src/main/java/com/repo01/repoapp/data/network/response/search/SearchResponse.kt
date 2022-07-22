package com.repo01.repoapp.data.network.response.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val items: List<SearchItemEntity>
)

data class SearchItemEntity(
    val name: String,
    val owner: OwnerEntity,
    val description: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    val language: String?
)

data class OwnerEntity(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?
)