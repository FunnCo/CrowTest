package com.funnco.crowtest.common.model.common

data class QuestionModel(
    val typeId: String,
    val description: String,
    val questionId: String,
    var number: Int,
    var body: AnswerModelHolder,
    var isAnswered : Boolean = false,
)