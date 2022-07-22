package com.repo01.repoapp.data.model

data class NotificationsItemModel(
    var id: String = "",
    var updatedAt: String = "",
    var title: String = "",
    var fullName: String = "",
    var commentNum: Int = 0,
    var orgImageUrl: String = "",
    var issueNumber: Int = 0
) {
    override fun toString(): String {
        return "id : ${id}, updatedAt : ${updatedAt}, title: ${title}, fullName : ${fullName}, commentNum : ${commentNum}, orgImageUrl : ${orgImageUrl}, issueNumber : ${issueNumber}"
    }
}
