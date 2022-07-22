package com.repo01.repoapp.util

import kotlin.math.ln
import kotlin.math.pow

fun getFormattedNumber(count: Int): String {
    if (count < 1000) return count.toString()
    val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
    return String.format("%.1f%c", count / 1000.0.pow(exp), "kMGTPE"[exp - 1])
}