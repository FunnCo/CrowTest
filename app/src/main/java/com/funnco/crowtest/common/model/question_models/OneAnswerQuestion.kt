package com.funnco.crowtest.common.model.question_models

import com.funnco.crowtest.common.model.AnswerModel

class OneAnswerQuestion(
    val task: String,
    val listOfImages: List<String>?,
    val answers: List<AnswerModel>,
    type: String,
): BaseQuestion(type)