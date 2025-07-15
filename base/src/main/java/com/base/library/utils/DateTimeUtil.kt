package com.base.library.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object DateTimeUtil {

    const val YYYY_MM_DD = "yyyy-MM-dd"
    const val YYYY_MM = "yyyy-MM"

    fun formatDateTime(timestamp: Long, formatter: String = "yyyy-MM-dd HH:mm"): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat(formatter, Locale.getDefault())
        return dateFormat.format(date)
    }

    fun formatTime2HM(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        return "${hours}小时${minutes}分"
    }

    fun formatTime2MS(millis: Int): String {
        val seconds = millis / 1000
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format(locale = Locale.getDefault(), "%02d:%02d", minutes, remainingSeconds)
    }
}