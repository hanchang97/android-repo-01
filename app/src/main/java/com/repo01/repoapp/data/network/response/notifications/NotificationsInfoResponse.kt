package com.repo01.repoapp.data.network.response.notifications

import com.google.gson.annotations.SerializedName
import com.repo01.repoapp.data.model.NotificationsInfoModel
import com.repo01.repoapp.util.DateCalculator

data class NotificationsInfoResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("repository")
    val repository: Repository,
    @SerializedName("subject")
    val subject: Subject
) {
    data class Repository(
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("owner")
        val owner: Owner
    ) {
        data class Owner(
            @SerializedName("login")
            val org: String,
            @SerializedName("avatar_url")
            val avataUrl: String
        )
    }

    data class Subject(
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val issueUrl: String
    )

    fun mapNotificationsMoedel() = NotificationsInfoModel(
        id = id,
        updatedAt = DateCalculator.parseDate(updatedAt),
        title = subject.title,
        fullName = repository.fullName,
        org = repository.owner.org,
        issueUrl = subject.issueUrl,
        avataUrl = repository.owner.avataUrl
    )
}