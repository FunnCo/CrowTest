package com.funnco.crowtest.common.model.question_models

import com.funnco.crowtest.common.model.AnswerModel

class MultipleAnswerQuestion(
    val task: String,
    type: String,
    val answers: List<AnswerModel>,
): BaseQuestion(type)