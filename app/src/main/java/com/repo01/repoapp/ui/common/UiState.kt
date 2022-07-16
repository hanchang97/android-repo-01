package com.repo01.repoapp.ui.common

sealed class UiState<out T>(val _data: T?, val message: String?) {

    data class Success<out T>(val data: T) : UiState<T>(
        _data = data,
        message = null
    )

    data class Error(val exception: String?) : UiState<Nothing>(
        _data = null,
        message = exception
    )

    object Loading : UiState<Nothing>(
        _data = null,
        message = null
    )

    object Empty : UiState<Nothing>(
        _data = null,
        message = null
    )
}
