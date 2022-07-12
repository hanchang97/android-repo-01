package com.repo01.repoapp.data.network.response

import com.google.gson.annotations.SerializedName

data class GithubAccessTokenResponse (
    @SerializedName("access_token")
    val accessToken : String
)