package com.funnco.crowtest.common.model.question_models

import com.funnco.crowtest.common.model.AnswerModel

class OneAnswerQuestion(
    testId: String,
    task: String,
    listOfImages: List<String>,
    answers: List<AnswerModel>,
    type: String,
): BaseQuestion(testId, type)