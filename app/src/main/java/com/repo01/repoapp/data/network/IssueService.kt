package com.repo01.repoapp.data.network

import com.repo01.repoapp.data.network.response.issue.IssueResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IssueService {
    @GET("user/issues")
    suspend fun getIssues(
        @Query("state") state: String,
        @Query("per_page") per_page: Int = 10,
        @Query("page") page: Int = 1
    ): Response<List<IssueResponse>>
}