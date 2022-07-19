package com.repo01.repoapp.data.repository

import com.repo01.repoapp.data.model.NotificationsInfoModel
import com.repo01.repoapp.data.network.NotificationsService
import com.repo01.repoapp.ui.common.UiState
import javax.inject.Inject

class NotificationsRepository @Inject constructor(private val notificationsService: NotificationsService) {
    suspend fun readNotification(threadId: Long) = notificationsService.readNotifications(threadId)

    suspend fun getNotifications(all: Boolean, page: Int): UiState<List<NotificationsInfoModel>> {
        runCatching {
            notificationsService.getNotifications(all, page)
        }.onSuccess { response ->
            return if (response.body() == null) {
                UiState.Error("Load Notifications Data Error")
            } else {
                UiState.Success(response.body()!!.map { notificationsInfoResponse ->
                    notificationsInfoResponse.mapNotificationsMoedel()
                })
            }
        }.onFailure { e ->
            return UiState.Error(e.message)
        }
        return UiState.Error("runCatching Error")
    }

}