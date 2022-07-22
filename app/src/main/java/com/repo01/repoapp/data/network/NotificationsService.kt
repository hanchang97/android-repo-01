package com.repo01.repoapp.data.network

import com.repo01.repoapp.data.network.response.notifications.NotificationsInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationsService {
    @GET("notifications")
    suspend fun getNotifications(
        @Query("all") all: Boolean,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = 10
    ): Response<List<NotificationsInfoResponse>>

    @PATCH("notifications/threads/{thread_id}")
    suspend fun readNotifications(
        @Path("thread_id") threadId: Long
    ): Response<Void>
}