package com.repo01.repoapp.data.repository

import com.repo01.repoapp.data.network.NotificationsService
import javax.inject.Inject

class NotificationsRepository @Inject constructor(private val notificationsService: NotificationsService) {
    suspend fun getNotifications(all: Boolean) = notificationsService.getNotifications(all)
    suspend fun readNotification(threadId: Long) = notificationsService.readNotifications(threadId)
}