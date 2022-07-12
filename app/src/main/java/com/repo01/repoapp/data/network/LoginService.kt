package com.repo01.repoapp.data.network

import com.repo01.repoapp.BuildConfig
import com.repo01.repoapp.data.network.response.GithubAccessTokenResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST


interface LoginService {
    @FormUrlEncoded
    @POST("login/oauth/access_token")
    @Headers("Accept: application/json")
    suspend fun getAccessToken(
        @Field("code") code: String,
        @Field("client_id") clientId: String = BuildConfig.GITHUB_CLIENT_ID,
        @Field("client_secret") clientSecret: String = BuildConfig.GITHUB_CLIENT_SECRET
    ): Response<GithubAccessTokenResponse>
}