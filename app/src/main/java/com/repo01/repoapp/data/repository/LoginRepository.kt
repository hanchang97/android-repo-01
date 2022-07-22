package com.repo01.repoapp.data.repository

import com.repo01.repoapp.data.network.LoginService
import com.repo01.repoapp.ui.common.UiState
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val service: LoginService
) {
    suspend fun getAccessToken(code: String): UiState<String> {
        runCatching {
            service.getAccessToken(code)
        }.onSuccess { response ->
            response.body()?.let {
                return UiState.Success(it.accessToken)
            } ?: return UiState.Empty
        }.onFailure {
            return UiState.Error(it.message)
        }
        return UiState.Error("run catching Error")
    }
}