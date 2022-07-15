package com.repo01.repoapp.ui.common

sealed class UiState<out T>(val _data: T?, val message: String?) {

    data class Success<out R>(val data: R) : UiState<R>(
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
