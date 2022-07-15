package com.repo01.repoapp.data.network.response.issue

import com.google.gson.annotations.SerializedName
import com.repo01.repoapp.data.model.SpecificIssueModel

data class SpecificIssueResponse(
    @SerializedName("comments")
    val commentNum: Int,
    @SerializedName("number")
    val number: Int
) {
    fun mapSpecificIssueModel() = SpecificIssueModel(
        commentNum = commentNum,
        issueNumber = number
    )
}
