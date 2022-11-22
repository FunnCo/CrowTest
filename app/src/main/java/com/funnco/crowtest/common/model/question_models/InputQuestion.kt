package com.funnco.crowtest.common.model.question_models

import com.funnco.crowtest.common.model.AnswerModel

class InputQuestion(
    testId: String,
    task: String,
    type: String,
    listOfImages: List<String>,
): BaseQuestion(testId, type)