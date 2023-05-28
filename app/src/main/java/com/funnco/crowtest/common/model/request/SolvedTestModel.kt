package com.funnco.crowtest.common.model.request

import com.funnco.crowtest.common.model.common.QuestionModel

data class SolvedTestModel(
    var answers: List<QuestionModel>? = null,
    var startSolvingDateTime: String? = null,
    var finishSolvingDateTime: String? = null,
)