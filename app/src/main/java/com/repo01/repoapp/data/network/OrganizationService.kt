package com.repo01.repoapp.data.network

import com.repo01.repoapp.data.network.response.organization.OrganizationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OrganizationService {
    @GET("orgs/{org}")
    suspend fun getOrganizationInfo(
        @Path("org") org: String,
    ): Response<OrganizationResponse>
}