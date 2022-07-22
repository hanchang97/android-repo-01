package com.repo01.repoapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.repo01.repoapp.data.model.SearchItemModel
import com.repo01.repoapp.data.network.SearchService
import com.repo01.repoapp.util.getFormattedNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val STARTING_PAGING_INDEX = 1

class SearchPagingSource(
    private val service: SearchService,
    private val query: String
) : PagingSource<Int, SearchItemModel>() {

    override fun getRefreshKey(state: PagingState<Int, SearchItemModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItemModel> {
        val page = params.key ?: STARTING_PAGING_INDEX
        return try {
            val response = service.getSearchRepositories(
                query = query,
                page = page,
                per_page = 30
            )

            val repos = response.body()?.items?.map { item ->
                SearchItemModel(
                    repoName = item.name,
                    ownerName = item.owner.login,
                    avatarUrl = item.owner.avatarUrl,
                    description = item.description,
                    stargazers_count = getFormattedNumber(item.stargazersCount),
                    language = item.language
                )
            } ?: listOf()

            LoadResult.Page(
                data = repos,
                prevKey = if (page == STARTING_PAGING_INDEX) null else page - 1,
                nextKey = if (repos.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}