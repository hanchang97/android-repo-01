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
import kotlin.math.ln
import kotlin.math.pow

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
                        stargazers_count = getFormattedNumber(item.stargazers_count),
                        language = item.language
                    )
                }
            }
        }
    }

    private fun getFormattedNumber(count: Int): String {
        if (count < 1000) return count.toString()
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f%c", count / 1000.0.pow(exp), "kMGTPE"[exp - 1])
    }
}