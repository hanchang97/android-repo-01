package com.repo01.repoapp.ui.main.tab.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repo01.repoapp.data.model.IssueItemModel
import com.repo01.repoapp.data.model.NotificationsInfoModel
import com.repo01.repoapp.data.model.NotificationsItemModel
import com.repo01.repoapp.data.repository.IssueRepository
import com.repo01.repoapp.data.repository.NotificationsRepository
import com.repo01.repoapp.data.repository.OrganizationRepository
import com.repo01.repoapp.util.PrintLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val issueRepository: IssueRepository,
    private val notificationsRepository: NotificationsRepository,
    private val organizationRepository: OrganizationRepository
) : ViewModel() {

    private val _notificationList = MutableLiveData<List<NotificationsItemModel>>()
    val notificationList: LiveData<List<NotificationsItemModel>> = _notificationList

    private val _progressBarVisible = MutableLiveData<Boolean>()
    val progressBarVisible: LiveData<Boolean> = _progressBarVisible
    // UI State 관리 리팩토링 에정 (loading, success, error)

    fun getNotifications() {
        _progressBarVisible.value = true
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

                val org = list[it].org

                val repoName = arr[arr.size - 3]
                PrintLog.printLog("repoName : ${repoName}")

                val issueNum = arr[arr.size - 1].toInt()
                PrintLog.printLog("issueNum : ${issueNum}")

                resultList[it].id = list[it].id
                resultList[it].title = list[it].title
                resultList[it].updatedAt = list[it].updatedAt
                resultList[it].fullName = list[it].fullName
                resultList[it].orgImageUrl = list[it].avataUrl

                val inx = it
                async {
                    val response = issueRepository.getSpecificIssue(org, repoName, issueNum)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            resultList[inx].commentNum = it.commentNum
                            resultList[inx].issueNumber = it.number
                            PrintLog.printLog("comment확인 [${inx}] : ${resultList[inx].commentNum} ")
                        }
                    }
                }
            }.awaitAll()

            resultList.forEach {
                PrintLog.printLog("${it.toString()}")
            }

            _notificationList.value = resultList
            _progressBarVisible.value = false
        }
    }
}