package com.repo01.repoapp.ui.main.tab.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repo01.repoapp.data.model.NotificationsInfoModel
import com.repo01.repoapp.data.model.NotificationsItemModel
import com.repo01.repoapp.data.repository.IssueRepository
import com.repo01.repoapp.data.repository.NotificationsRepository
import com.repo01.repoapp.data.repository.OrganizationRepository
import com.repo01.repoapp.ui.common.UiState
import com.repo01.repoapp.util.PrintLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val issueRepository: IssueRepository,
    private val notificationsRepository: NotificationsRepository
) : ViewModel() {

    private val _notificationList = MutableLiveData<List<NotificationsItemModel>>()
    private val notificationListForUpdate = mutableListOf<NotificationsItemModel>()
    val notificationList: LiveData<List<NotificationsItemModel>> = _notificationList

    private val _progressBarVisible = MutableLiveData<Boolean>()
    val progressBarVisible: LiveData<Boolean> = _progressBarVisible

    private val _notificationState = MutableLiveData<UiState<List<NotificationsInfoModel>>>()
    val notificationState: LiveData<UiState<List<NotificationsInfoModel>>> = _notificationState

    var currentPage = 1
    var addtionalNotificationState: UiState<Any> = UiState.Empty


    fun getNotifications(all: Boolean) {
        _notificationState.value = UiState.Loading
        viewModelScope.launch {
            _notificationState.value = notificationsRepository.getNotifications(all, currentPage)
        }
    }

    fun getAdditionalNotificationsInfo(list: List<NotificationsInfoModel>) {
        val resultList = List(list.size) { NotificationsItemModel() }
        setProgressBarVisibility(true)
        addtionalNotificationState = UiState.Loading

        viewModelScope.launch {
            (list.indices).map {
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

            notificationListForUpdate.addAll(resultList)
            _notificationList.value = notificationListForUpdate
            setProgressBarVisibility(false)
            addtionalNotificationState = UiState.Success(Any())
            if(resultList.isNotEmpty()) currentPage++

            // 추가 api 호출 시 에러 처리 필요
        }
    }

    fun readNotification(threadId: Long){
        viewModelScope.launch {
            val response = notificationsRepository.readNotification(threadId)

            if(response.isSuccessful){
                PrintLog.printLog("read success")
            }
            else{
                PrintLog.printLog("read error")
            }
        }
    }

    fun setProgressBarVisibility(isVisible: Boolean){
        _progressBarVisible.value = isVisible
    }
}