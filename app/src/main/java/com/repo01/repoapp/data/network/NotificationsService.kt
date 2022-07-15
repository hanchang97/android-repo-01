package com.repo01.repoapp.data.network

import com.repo01.repoapp.data.network.response.notifications.NotificationsInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationsService {
    @GET("notifications")
    suspend fun getNotifications(
        @Query("all") all: Boolean,
        @Query("per_page") per_page: Int = 10,
        @Query("page") page: Int = 1
    ): Response<List<NotificationsInfoResponse>>
}