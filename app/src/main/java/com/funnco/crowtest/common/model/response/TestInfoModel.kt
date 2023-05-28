package com.funnco.crowtest.common.model.response

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

data class TestInfoModel (
    val testId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val authorId: String? = null,
    val subjectName: String? = null,
    val startDate: Date? = null,
    val deadlineDate: Date? = null,
    val duration: Long? = null,
) {

    fun getPrettyTime(): String {
        return String.format(
            "${TimeUnit.MILLISECONDS.toMinutes(duration!!)} мин ${
                TimeUnit.MILLISECONDS.toSeconds(
                    duration
                ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            } сек"
        )
    }

    fun getPrettyStartLineDate(): String {
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale("ru"))
        return format.format(startDate)
    }

    fun getPrettyDeadLineDate(): String {
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale("ru"))
        return format.format(deadlineDate)
    }
}