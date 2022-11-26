package com.funnco.crowtest.common.model.question_models

import com.funnco.crowtest.common.model.AnswerModel

class InputQuestion(
    val task: String,
    type: String,
    var answer: AnswerModel? = null
): BaseQuestion(type)