package com.repo01.repoapp.ui.main.tab.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repo01.repoapp.data.model.NotificationsInfoModel
import com.repo01.repoapp.data.model.NotificationsItemModel
import com.repo01.repoapp.data.repository.IssueRepository
import com.repo01.repoapp.data.repository.NotificationsRepository
import com.repo01.repoapp.util.PrintLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val issueRepository: IssueRepository,
    private val notificationsRepository: NotificationsRepository
) : ViewModel() {

    fun getNotifications() {
        viewModelScope.launch {
            val response = notificationsRepository.getNotifications(true)

            if (response.isSuccessful) {
                response.body()?.let {
                    val list = it.map { noti -> noti.mapNotificationsMoedel() }
                    getAdditionalNotificationsInfo(list)
                }
            }
        }
    }

    fun getAdditionalNotificationsInfo(list: List<NotificationsInfoModel>) {
        val resultList = List(list.size) { NotificationsItemModel() }
        viewModelScope.launch {
            (0..list.size - 1).map {
                val str = list[it].issueUrl.substring(8, list[it].issueUrl.length)
                PrintLog.printLog("substring : ${str}")

                val arr = str.split("/")

                val owner = list[it].org

                val repoName = arr[arr.size - 3]
                PrintLog.printLog("repoName : ${repoName}")

                val issueNum = arr[arr.size - 1].toInt()
                PrintLog.printLog("issueNum : ${issueNum}")

                resultList[it].id = list[it].id
                resultList[it].title = list[it].title
                resultList[it].updatedAt = list[it].updatedAt
                resultList[it].fullName = list[it].fullName

                // NotificationsItemModel(it.id, it.updatedAt, it.title, it.fullName)

                val inx = it
                async {
                    val response = issueRepository.getSpecificIssue(owner, repoName, issueNum)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            resultList[inx].commentNum = it.commentNum
                            PrintLog.printLog("comment확인 : ${it.commentNum} ")
                        }
                    }
                }

            }.awaitAll()

            resultList.forEach {
                PrintLog.printLog("comment : ${it.commentNum}")
            }
        }
    }
}