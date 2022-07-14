package com.repo01.repoapp.data.repository

import com.repo01.repoapp.data.network.IssueService
import javax.inject.Inject

class IssueRepository @Inject constructor(private val issueService: IssueService) {
    suspend fun getIssues(state: String) = issueService.getIssues(state)
}