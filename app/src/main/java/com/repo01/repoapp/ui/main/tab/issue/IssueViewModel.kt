package com.repo01.repoapp.ui.main.tab.issue

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repo01.repoapp.data.model.IssueItemModel
import com.repo01.repoapp.data.repository.IssueRepository
import com.repo01.repoapp.util.PrintLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(private val issueRepository: IssueRepository): ViewModel() {

    private val _optionIndex = MutableLiveData<Int>(-1)
    val optionIndex: LiveData<Int> = _optionIndex

    private val _issueList = MutableLiveData<List<IssueItemModel>>()
    val issueList: LiveData<List<IssueItemModel>> = _issueList

    fun updateOptionIndex(inx: Int){
        _optionIndex.value = inx
    }

    fun getIssues(state: String){

        viewModelScope.launch {
            val response = issueRepository.getIssues(state)
            if(response.isSuccessful){
                response.body()?.let {
                    PrintLog.printLog(it.size.toString())
                    _issueList.value = it.map { issue -> issue.MapIssueItemMoedel() }
                }
            }
        }
    }
}