package com.funnco.crowtest.common.model.question_models

import com.funnco.crowtest.common.model.AnswerModel

class OneAnswerQuestion(
    val task: String,
    type: String,
    val answers: List<AnswerModel>,
): BaseQuestion(type)