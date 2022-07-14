package com.repo01.repoapp.ui.main.tab.issue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(): ViewModel() {

    private val _optionIndex = MutableLiveData<Int>(-1)
    val optionIndex: LiveData<Int> = _optionIndex

    fun updateOptionIndex(inx: Int){
        _optionIndex.value = inx
    }
}