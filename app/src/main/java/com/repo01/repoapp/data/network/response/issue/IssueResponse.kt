package com.repo01.repoapp.data.network.response.issue

import com.google.gson.annotations.SerializedName
import com.repo01.repoapp.data.model.IssueItemModel

data class IssueResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("state")
    val state: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("repository")
    val repository: Repository
) {
    data class Repository(
        @SerializedName("full_name")
        val fullName: String
    )

    fun MapIssueItemMoedel() = IssueItemModel(
        id = id,
        state = state,
        createdAt = createdAt,
        title = title,
        number = number,
        fullName = repository.fullName
    )
}
