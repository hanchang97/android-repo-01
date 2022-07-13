package com.repo01.repoapp.util


object Auth {
    const val AUTH_BASE_URL = "https://github.com/login/oauth/authorize"
    const val AUTH_PARAM_CLIENT_ID = "client_id"
    const val AUTH_PARAM_REDIRECT_URI = "redirect_uri"
    const val AUTH_PARAM_SCOPE = "scope"

    const val REDIRECT_URI = "repo://github-auth"
    const val SCOPE = "repo, user, notifications"
}