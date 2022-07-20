package com.repo01.repoapp.data.model

import android.graphics.Color

data class SearchItemModel(
    val repoName: String,
    val ownerName: String,
    val avatarUrl: String?,
    val description: String?,
    val stargazers_count: String,
    val language: String?
) {
    companion object {
        val map = mutableMapOf<String, Int>()
    }

    val color: Int = language?.let {
        if (map.containsKey(it).not()) {
            map[it] = randomColor()
        }
        map[it]
    } ?: Color.TRANSPARENT

    private fun randomColor(): Int {
        val r = (0..255).random()
        val g = (0..255).random()
        val b = (0..255).random()
        return Color.rgb(r, g, b)
    }
}