package com.funnco.crowtest.common.model.response

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

data class ResponseSolvedTestInfo (
    var testId: String,
    var mark: Int,
    var solveDate: Date,
    var timeSolving: Long,
    var title: String? = null
){

    fun getPrettyTimeSolving(): String {
        return String.format(
            "${TimeUnit.MILLISECONDS.toMinutes(timeSolving)} мин ${
                TimeUnit.MILLISECONDS.toSeconds(
                    timeSolving
                ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeSolving))
            } сек"
        )
    }

    fun getPrettySolveDate(): String {
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale("ru"))
        return format.format(solveDate)
    }

}
