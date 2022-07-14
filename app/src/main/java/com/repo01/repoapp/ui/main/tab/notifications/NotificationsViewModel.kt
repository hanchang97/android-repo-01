package com.repo01.repoapp.ui.main.tab.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repo01.repoapp.data.repository.NotificationsRepository
import com.repo01.repoapp.util.PrintLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(private val notificationsRepository: NotificationsRepository) : ViewModel() {

    fun getNotifications() {
        viewModelScope.launch {
            val response = notificationsRepository.getNotifications(true)

            if (response.isSuccessful) {
                response.body()?.let {
                    val list = it.map { noti -> noti.mapNotificationsMoedel() }

                    list.forEach {
                        PrintLog.printLog("result : ${it}")

                        val str = it.issueUrl.substring(8, it.issueUrl.length)
                        PrintLog.printLog("substring : ${str}")

                        val arr = str.split("/")

                        val repoName = arr[arr.size-3]
                        PrintLog.printLog("repoName : ${repoName}")

                        val issueNum = arr[arr.size-1].toInt()
                        PrintLog.printLog("issueNum : ${issueNum}")
                    }
                }
            }
        }
    }
}