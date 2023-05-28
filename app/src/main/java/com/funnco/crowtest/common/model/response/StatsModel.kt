package com.funnco.crowtest.common.model.response

import java.util.concurrent.TimeUnit

data class StatsModel(
  var averageMark: Double,
  var averageSolveTime: Long,
  var correctAnswerRatio: Double,
  var totalTestsSolved: Int
){

  fun getPrettyTime(): String {
    return String.format(
      "${TimeUnit.MILLISECONDS.toMinutes(averageSolveTime)} мин ${
        TimeUnit.MILLISECONDS.toSeconds(
          averageSolveTime
        ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(averageSolveTime))
      } сек"
    )
  }
}