package com.funnco.crowtest.repository

import com.funnco.crowtest.common.model.TestModel

object Repository {
    fun loadAvailableTests(response: (List<TestModel>) -> Unit) {
        response(
            listOf(
                TestModel(
                    "uuid1",
                    "Параллельность плоскостей",
                    null,
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
                    null,
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
                    null,
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
                    null,
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
                    null,
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
                    null,
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
                    null,
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
                    null,
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