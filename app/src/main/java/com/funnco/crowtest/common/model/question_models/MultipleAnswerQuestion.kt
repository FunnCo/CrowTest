package com.funnco.crowtest.common.model.question_models

import com.funnco.crowtest.common.model.AnswerModel

class MultipleAnswerQuestion(
    testId: String,
    task: String,
    type: String,
    listOfImages: List<String>,
    answers: List<AnswerModel>,
): BaseQuestion(testId, type)