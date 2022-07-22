package com.repo01.repoapp.data.repository

import com.repo01.repoapp.data.network.OrganizationService
import javax.inject.Inject

class OrganizationRepository @Inject constructor(private val organizationService: OrganizationService) {
    suspend fun getOrganizationInfo(org: String) = organizationService.getOrganizationInfo(org)
}