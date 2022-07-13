package com.repo01.repoapp.data.repository

import com.repo01.repoapp.data.network.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: SearchService
) {
    suspend fun getSearchRepositories(query: String) = withContext(Dispatchers.IO) {
        service.getSearchRepositories(query)
    }
}