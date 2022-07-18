package com.repo01.repoapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.repo01.repoapp.data.network.SearchService
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: SearchService
) {
    fun getSearchRepositories(query: String) = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            SearchPagingSource(service = service, query = query)
        }
    ).liveData

}