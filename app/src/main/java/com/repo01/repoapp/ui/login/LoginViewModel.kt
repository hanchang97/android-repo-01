package com.repo01.repoapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repo01.repoapp.data.repository.LoginRepository
import com.repo01.repoapp.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<UiState<String>>()
    val uiState: LiveData<UiState<String>> = _uiState

    private val _loginClickEvent = MutableLiveData<Unit>()
    val loginClickEvent: LiveData<Unit> = _loginClickEvent

    fun getAccessToken(code: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            _uiState.value = repository.getAccessToken(code = code)
        }
    }

    fun onLoginButtonClicked() {
        _loginClickEvent.postValue(Unit)
    }
}