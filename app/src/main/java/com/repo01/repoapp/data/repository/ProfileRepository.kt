package com.repo01.repoapp.data.repository

import com.repo01.repoapp.data.model.ProfileModel
import com.repo01.repoapp.data.network.ProfileService
import com.repo01.repoapp.ui.common.UiState
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val service: ProfileService
) {
    suspend fun getUserInformation(): UiState<ProfileModel> {
        runCatching {
            service.getAuthenticatedUser()
        }.onSuccess { response ->
            return if (response.body() != null) {
                when (val starredResponse = getStarredRepository()) {
                    is UiState.Success -> {
                        UiState.Success(
                            data = response.body()!!.toProfileModel(
                                starredResponse.data
                            )
                        )
                    }
                    else -> {
                        UiState.Error(starredResponse.message)
                    }
                }
            } else {
                UiState.Empty
            }
        }.onFailure {
            return UiState.Error(it.message)
        }
        return UiState.Error("run catching error")
    }

    private suspend fun getStarredRepository(): UiState<Int> {
        runCatching {
            service.getStarredRepository()
        }.onSuccess { response ->
            return if (response.body() != null) {
                UiState.Success(
                    data = response.body()!!.size
                )
            } else {
                UiState.Empty
            }
        }.onFailure {
            return UiState.Error(it.message)
        }
        return UiState.Error("run catching error")
    }
}