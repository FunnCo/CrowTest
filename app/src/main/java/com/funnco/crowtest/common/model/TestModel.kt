package com.funnco.crowtest.common.model

class TestModel(
    val id: String,
    val heading : String,
    val image: String?,
    val description: String,
    val deadlineDate: String,
    val solveDate: String?,
    val mark: String?,
    val timeForSolving: Int,
    val timeUsedToSolve: Float?

    // Сами вопросы будут приходить отдельно
)