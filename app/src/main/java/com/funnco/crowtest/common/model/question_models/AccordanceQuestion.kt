package com.funnco.crowtest.common.model.question_models

import com.funnco.crowtest.common.model.AnswerModel

class AccordanceQuestion(
    val task: String,
    type: String,
    val firstListOfAnswers: List<AnswerModel>,
    var secondListOfAnswers: List<AnswerModel>
) : BaseQuestion(type)