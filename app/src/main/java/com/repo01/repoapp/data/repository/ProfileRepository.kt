package com.repo01.repoapp.data.repository

import com.repo01.repoapp.data.network.ProfileService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val service: ProfileService
) {
    suspend fun getUserInformation() = withContext(Dispatchers.IO) {
        service.getAuthenticatedUser()
    }

    suspend fun getStarredRepository() = withContext(Dispatchers.IO) {
        service.getStarredRepository()
    }
}