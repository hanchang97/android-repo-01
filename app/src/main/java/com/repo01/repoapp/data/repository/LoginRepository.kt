package com.repo01.repoapp.data.repository

import com.repo01.repoapp.data.network.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val service: LoginService
) {
    suspend fun getAccessToken(code: String) = service.getAccessToken(code)
}