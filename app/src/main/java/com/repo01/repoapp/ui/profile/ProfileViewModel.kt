package com.repo01.repoapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repo01.repoapp.data.model.ProfileModel
import com.repo01.repoapp.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {
    private val _userInfo = MutableLiveData<ProfileModel>()
    val userInfo: LiveData<ProfileModel> = _userInfo

    private val _starredCount = MutableLiveData<Int>()
    val starredCount: LiveData<Int> = _starredCount

    init {
        getUserInformation()
        getStarredRepoCount()
    }

    private fun getUserInformation() = viewModelScope.launch {
        val response = repository.getUserInformation()
        if (response.isSuccessful) {
            response.body()?.let {
                _userInfo.value = it.toProfileModel()
            }
        }
    }

    private fun getStarredRepoCount() = viewModelScope.launch {
        val response = repository.getStarredRepository()
        if (response.isSuccessful) {
            response.body()?.let {
                _starredCount.value = it.size
            }
        }
    }
}