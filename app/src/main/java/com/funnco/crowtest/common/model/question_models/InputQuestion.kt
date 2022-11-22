package com.funnco.crowtest.common.model.question_models

import com.funnco.crowtest.common.model.AnswerModel

class InputQuestion(
    val task: String,
    type: String,
    val listOfImages: List<String>?,
): BaseQuestion(type)