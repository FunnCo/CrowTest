package com.funnco.crowtest.common.model

class TestModel(
    val id: String,
    val heading : String,
    val description: String,
    val deadlineDate: String,
    val startDate: String,
    val solveDate: String?,
    val mark: String?,
    val timeForSolving: Int,
    var timeUsedToSolve: Float?

    // Сами вопросы будут приходить отдельно
)