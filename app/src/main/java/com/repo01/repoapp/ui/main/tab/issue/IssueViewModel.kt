package com.repo01.repoapp.ui.main.tab.issue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repo01.repoapp.data.model.IssueItemModel
import com.repo01.repoapp.data.repository.IssueRepository
import com.repo01.repoapp.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(private val issueRepository: IssueRepository) :
    ViewModel() {

    private val _optionIndex = MutableLiveData<Int>(-1)
    val optionIndex: LiveData<Int> = _optionIndex

    private val _issueState = MutableLiveData<UiState<List<IssueItemModel>>>()
    val issueState: LiveData<UiState<List<IssueItemModel>>> = _issueState

    fun updateOptionIndex(inx: Int) {
        _optionIndex.value = inx
    }

    fun getIssues(state: String) {
        _issueState.value = UiState.Loading
        viewModelScope.launch {
            _issueState.value = issueRepository.getIssues(state)
        }
    }
}