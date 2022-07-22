package com.repo01.repoapp.data.network

import com.repo01.repoapp.data.network.response.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/search/repositories")
    suspend fun getSearchRepositories(
        @Query("q") query: String,
        @Query("per_page") per_page: Int? = 30,
        @Query("page") page: Int? = 1
    ): Response<SearchResponse>
}