package com.repo01.repoapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repo01.repoapp.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun getAccessToken(code: String) =
        viewModelScope.launch {
            val response = repository.getAccessToken(code = code)

            if (response.isSuccessful) {
                response.body()?.let {
                    _token.value = it.accessToken
                }
            }
        }
}