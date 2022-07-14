package com.repo01.repoapp.data.network

import com.repo01.repoapp.data.network.response.profile.ProfileResponse
import com.repo01.repoapp.data.network.response.profile.StarredResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProfileService {
    @GET("/user")
    suspend fun getAuthenticatedUser(): Response<ProfileResponse>

    @GET("/user/starred")
    suspend fun getStarredRepository(): Response<List<StarredResponse>>

}