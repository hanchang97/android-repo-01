package com.repo01.repoapp.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.repo01.repoapp.data.model.SearchItemModel
import com.repo01.repoapp.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData<String>()

    private val _result = currentQuery.switchMap {
        repository.getSearchRepositories(it).cachedIn(viewModelScope)
    }
    val result: LiveData<PagingData<SearchItemModel>> = _result

    private var searchJob: Job? = null

    fun cancelSearchJob() = searchJob?.cancel()

    fun searchRepos(query: String) {
        cancelSearchJob()
        searchJob = viewModelScope.launch {
            delay(500)
            currentQuery.value = query
        }
    }
}