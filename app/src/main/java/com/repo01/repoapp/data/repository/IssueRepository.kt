package com.repo01.repoapp.data.repository

import com.repo01.repoapp.data.model.IssueItemModel
import com.repo01.repoapp.data.network.IssueService
import com.repo01.repoapp.ui.common.UiState
import javax.inject.Inject

class IssueRepository @Inject constructor(private val issueService: IssueService) {
    suspend fun getSpecificIssue(owner: String, repo: String, issueNumber: Int) =
        issueService.getSpecificIssue(owner, repo, issueNumber)

    suspend fun getIssues(state: String): UiState<List<IssueItemModel>> {
        runCatching {
            issueService.getIssues(state)
        }.onSuccess { response ->
            return if (response.body() == null) {
                UiState.Error("Load Issue Data Error")
            } else {
                UiState.Success(
                    response.body()!!.map { issueResponse -> issueResponse.mapIssueItemMoedel() })
            }
        }.onFailure { e ->
            return UiState.Error(e.message)
        }
        return UiState.Error("runCatching Error")
    }
}