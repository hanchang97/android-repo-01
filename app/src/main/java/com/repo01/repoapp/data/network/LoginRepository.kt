package com.repo01.repoapp.data.network

import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val service: LoginService
) {
    suspend fun getAccessToken(code: String) = service.getAccessToken(code)
}