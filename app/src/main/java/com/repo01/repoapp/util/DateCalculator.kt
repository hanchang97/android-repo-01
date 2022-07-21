package com.repo01.repoapp.util

import java.text.SimpleDateFormat
import java.util.*

object DateCalculator {

    fun parseDate(dateStr: String): String {
        try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREAN)
            val currentDate = Calendar.getInstance().apply {
                timeInMillis = Date().time
            }

            val targetDate = Calendar.getInstance().apply {
                timeInMillis = parser.parse(dateStr)?.time ?: 0L
            }

            val difference =
                getIgnoredTimeDays(currentDate.timeInMillis) - getIgnoredTimeDays(targetDate.timeInMillis)
            val differDay = (difference / 24 / 60 / 60 / 1000).toInt()
            return when (differDay) {
                0 -> {
                    "오늘"
                }
                else -> {
                    "${differDay}일 전"
                }
            }
        } catch (e: Exception) {
            return "X"
        }
    }

    // 일수 차이로 계산하기 위함
    fun getIgnoredTimeDays(time: Long): Long {
        return Calendar.getInstance().apply {
            timeInMillis = time

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}