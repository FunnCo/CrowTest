package com.funnco.crowtest.activity.test.question

class CurrentTest private constructor(){
    companion object{
        private var instance: CurrentTest? = null

        fun getInstanceOfTest(): CurrentTest{
            return if(instance == null){
                CurrentTest()
            } else {
                instance!!
            }
        }

        fun nullifyInstance(){
            instance = null
        }
    }

    /** TODO: Остановился здесь, делай дальше отсюда.
     *
     *  TODO: Надо сделать сохранение выбранных ответов сюда
     *  TODO: Надо сделать отображение вариантов ответов во фрагментах
     *  TODO: Надо сделать отображение времени прохождения в деталях теста
     *  TODO: Надо сделать отображение оставшегося времени в прохождении теста
     *  TODO: Надо сделать включение и выключения кнопки "Завершить тест" при ответе на все вопросы
     *  TODO: Надо сделать диалог с подтверждением при нажатии кнопки назад в тесте
     */


}