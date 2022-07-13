package com.repo01.repoapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repo01.repoapp.data.model.SearchItemModel
import com.repo01.repoapp.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {
    private val _searchResult = MutableLiveData<List<SearchItemModel>>()
    val searchResult: LiveData<List<SearchItemModel>> = _searchResult

    fun searchRepositoriesByQuery(query: String) = viewModelScope.launch {
        val response = repository.getSearchRepositories(query)
        if (response.isSuccessful) {
            response.body()?.let {
                _searchResult.value = it.items.map { item ->
                    SearchItemModel(
                        repoName = item.name,
                        ownerName = item.owner.login,
                        avatarUrl = item.owner.avatar_url,
                        description = item.description,
                        stargazers_count = item.stargazers_count,
                        language = item.language
                    )
                }
            }
        }
    }
}