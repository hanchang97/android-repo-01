package com.repo01.repoapp.data.network.response.organization

import com.google.gson.annotations.SerializedName
import com.repo01.repoapp.data.model.OrganizationModel

data class OrganizationResponse(
    @SerializedName("avatar_url")
    val imageUrl: String
) {
    fun mapOrganizationModel() = OrganizationModel(imageUrl = imageUrl)
}
