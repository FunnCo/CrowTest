package com.funnco.crowtest.repository

import com.funnco.crowtest.common.model.AnswerModel
import com.funnco.crowtest.common.model.TestModel
import com.funnco.crowtest.common.model.question_models.*

object Repository {

    lateinit var downLoadedTests: List<TestModel>

    fun sendAnswers(testId: String, questions: List<BaseQuestion>, response: (TestModel) -> Unit) {
        getTestById(testId){
            response(it)
        }
    }

    fun loadQuestions(
        testId: String,
        response: (questions: List<BaseQuestion>, test: TestModel) -> Unit
    ) {
        response(
            listOf(
                OneAnswerQuestion(
                    "Выберите правильный вариант ответ",
                    "one_answer",
                    listOf(
                        AnswerModel(
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            "4 вариант ответа"
                        ),
                    ),
                ),
                MultipleAnswerQuestion(
                    "Выберите всe правильные варианты ответа",
                    "multiple_answer",
                    listOf(
                        AnswerModel(
                            "1 вариант ответа"
                        ),
                        AnswerModel(
                            "2 вариант ответа"
                        ),
                        AnswerModel(
                            "3 вариант ответа"
                        ),
                        AnswerModel(
                            "4 вариант ответа"
                        ),
                    )
                ),

                InputQuestion(
                    "Введите правильный ответ",
                    "input_answer",
                ),
                AccordanceQuestion(
                    "Расположите события по соотвествующим им годам",
                    "accordance_answer",
                    listOf<AnswerModel>(
                        AnswerModel("Великая отечественная война"),
                        AnswerModel("Путч и еще немного длинных слов для большой карточки"),
                        AnswerModel("Отечественная война"),
                    ),
                    listOf<AnswerModel>(
                        AnswerModel("1812"),
                        AnswerModel("1991"),
                        AnswerModel("1039"),
                    ),
                )
            ),
            downLoadedTests.find { it.id == testId }!!
        )
    }

    fun getTestById(testId: String, response: (TestModel) -> Unit) {
        response(downLoadedTests.find { it.id == testId }!!)
    }

    fun loadAvailableTests(response: (List<TestModel>) -> Unit) {

        loadDoneTests {
            downLoadedTests = it
            response(
                downLoadedTests
            )
        }

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
                    "20.11.2022",
                    "01.12.2022",
                    "2",
                    1,
                    28.0f
                ),
                TestModel(
                    "uuid3",
                    "С++, циклы",
                    "Проверочный тест",
                    "01.12.2022",
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
                    "15.01.2023",
                    "4",
                    20,
                    0.5f
                ),
            )
        )
    }
}