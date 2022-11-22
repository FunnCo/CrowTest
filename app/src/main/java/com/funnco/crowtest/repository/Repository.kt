package com.funnco.crowtest.repository

import com.funnco.crowtest.common.model.AnswerModel
import com.funnco.crowtest.common.model.TestModel
import com.funnco.crowtest.common.model.question_models.*

object Repository {
    fun loadQuestions(testId: String, response: (List<BaseQuestion>) -> Unit) {
        response(
            listOf(
                OneAnswerQuestion(
                    "Выберите правильный вариант ответ",
                    null,
                    listOf(
                        AnswerModel(
                            null,
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "4 вариант ответа"
                        ),
                    ),
                    "one_answer"
                ),
                OneAnswerQuestion(
                    "Выберите правильный вариант ответ",
                    null,
                    listOf(
                        AnswerModel(
                            null,
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "4 вариант ответа"
                        ),
                    ),
                    "one_answer"
                ),
                OneAnswerQuestion(
                    "Выберите правильный вариант ответ",
                    null,
                    listOf(
                        AnswerModel(
                            null,
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "4 вариант ответа"
                        ),
                    ),
                    "one_answer"
                ),
                OneAnswerQuestion(
                    "Выберите правильный вариант ответ",
                    null,
                    listOf(
                        AnswerModel(
                            null,
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "4 вариант ответа"
                        ),
                    ),
                    "one_answer"
                ),
                OneAnswerQuestion(
                    "Выберите правильный вариант ответ",
                    null,
                    listOf(
                        AnswerModel(
                            null,
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "4 вариант ответа"
                        ),
                    ),
                    "one_answer"
                ),
                OneAnswerQuestion(
                    "Выберите правильный вариант ответ",
                    null,
                    listOf(
                        AnswerModel(
                            null,
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "4 вариант ответа"
                        ),
                    ),
                    "one_answer"
                ),
                OneAnswerQuestion(
                    "Выберите правильный вариант ответ",
                    null,
                    listOf(
                        AnswerModel(
                            null,
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "4 вариант ответа"
                        ),
                    ),
                    "one_answer"
                ),
                OneAnswerQuestion(
                    "Выберите правильный вариант ответ",
                    null,
                    listOf(
                        AnswerModel(
                            null,
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "4 вариант ответа"
                        ),
                    ),
                    "one_answer"
                ),
                OneAnswerQuestion(
                    "Выберите правильный вариант ответ",
                    null,
                    listOf(
                        AnswerModel(
                            null,
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "4 вариант ответа"
                        ),
                    ),
                    "one_answer"
                ),
                OneAnswerQuestion(
                    "Выберите правильный вариант ответ",
                    null,
                    listOf(
                        AnswerModel(
                            null,
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "4 вариант ответа"
                        ),
                    ),
                    "one_answer"
                ),
                MultipleAnswerQuestion(
                    "Выберите вес правильные варианты ответа",
                    "multiple_answer",
                    null,
                    listOf(
                        AnswerModel(
                            null,
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            null,
                            "4 вариант ответа"
                        ),
                    )
                ),
                InputQuestion(
                    "Введите правильный ответ",
                    "input_answer",
                    null
                ),
                AccordanceQuestion(
                    "accordance_answer"
                )
            )
        )
    }

    fun loadAvailableTests(response: (List<TestModel>) -> Unit) {
        response(
            listOf(
                TestModel(
                    "uuid1",
                    "Параллельность плоскостей",
                    "Самостоятельная работа по теме параллельность плоскостей",
                    "01.12.2022",
                    null,
                    null,
                    30,
                    null
                ),
                TestModel(
                    "uuid2",
                    "Физкультура",
                    "Разминка и подборка упражнений №5",
                    "20.11.2022",
                    null,
                    null,
                    40,
                    null
                ),
                TestModel(
                    "uuid3",
                    "С++, циклы",
                    "Проверочный тест",
                    "01.12.2022",
                    null,
                    null,
                    15,
                    null
                ),
                TestModel(
                    "uuid4",
                    "Геном человека",
                    "Домашняя работа по параграфу 12",
                    "15.01.2023",
                    null,
                    null,
                    20,
                    null
                ),
            )
        )
    }

    fun loadDoneTests(response: (List<TestModel>) -> Unit) {
        response(
            listOf(
                TestModel(
                    "uuid1",
                    "Параллельность плоскостей",
                    "Самостоятельная работа по теме параллельность плоскостей",
                    "01.12.2022",
                    "01.12.2022",
                    "5",
                    30,
                    11f
                ),
                TestModel(
                    "uuid2",
                    "Физкультура",
                    "Разминка и подборка упражнений №5",
                    "20.11.2022",
                    "01.12.2022",
                    "2",
                    40,
                    28.0f
                ),
                TestModel(
                    "uuid3",
                    "С++, циклы",
                    "Проверочный тест",
                    "01.12.2022",
                    "01.12.2022",
                    "3",
                    15,
                    12.4f
                ),
                TestModel(
                    "uuid4",
                    "Геном человека",
                    "Домашняя работа по параграфу 12",
                    "15.01.2023",
                    "15.01.2023",
                    "4",
                    20,
                    0.5f
                ),
            )
        )
    }
}