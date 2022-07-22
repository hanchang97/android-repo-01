package com.repo01.repoapp.data.network.response.issue

import com.google.gson.annotations.SerializedName
import com.repo01.repoapp.data.model.IssueItemModel
import com.repo01.repoapp.util.DateCalculator

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

    fun mapIssueItemMoedel() = IssueItemModel(
        id = id,
        state = state,
        createdAt = DateCalculator.parseDate(createdAt),
        title = title,
        number = number,
        fullName = repository.fullName
    )
}

