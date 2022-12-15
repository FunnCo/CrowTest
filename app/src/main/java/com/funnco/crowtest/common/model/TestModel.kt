package com.funnco.crowtest.common.model

import java.util.concurrent.TimeUnit

class TestModel(
    val id: String,
    val heading: String,
    val description: String,
    val deadLineDate: String,
    val startDate: String,
    val solveDate: String? = null,
    val mark: String? = null,
    val timeForSolving: Int? = null,
    var solvingTime: Float? = null
) {

    fun getPrettyTimeSolving(): String {
        val timeInMillis = (solvingTime!! * 60000).toLong()
        return String.format(
            "${TimeUnit.MILLISECONDS.toMinutes(timeInMillis)} мин ${
                TimeUnit.MILLISECONDS.toSeconds(
                    timeInMillis
                ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMillis))
            } сек"
        )
    }
}