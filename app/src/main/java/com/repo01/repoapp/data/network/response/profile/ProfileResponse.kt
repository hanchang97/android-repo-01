package com.repo01.repoapp.data.network.response.profile

import com.google.gson.annotations.SerializedName
import com.repo01.repoapp.data.model.ProfileModel

data class ProfileResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("name")
    val displayName: String?,
    @SerializedName("blog")
    val blogLink: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("bio")
    val state: String?,
    @SerializedName("public_repos")
    val publicRepoCount: Int,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("total_private_repos")
    val privateRepoCount: Int
) {
    fun toProfileModel() = ProfileModel(
        id = id,
        userName = userName,
        displayName = displayName,
        avatarUrl = avatarUrl,
        blogLink = blogLink.setNullOrValue(),
        location = location.setNullOrValue(),
        email = email.setNullOrValue(),
        state = state.setNullOrValue(),
        followers = followers,
        following = following,
        repoCount = publicRepoCount + privateRepoCount
    )

    private fun String?.setNullOrValue() = if (this.isNullOrEmpty()) null else this
}