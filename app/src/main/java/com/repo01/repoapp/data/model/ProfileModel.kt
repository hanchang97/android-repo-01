package com.repo01.repoapp.data.model

data class ProfileModel(
    val id: Int,
    val userName: String,
    val displayName: String?,
    val avatarUrl: String,
    val blogLink: String?,
    val location: String?,
    val email: String?,
    val state: String?,
    val followers: Int,
    val following: Int,
    val repoCount: Int
)