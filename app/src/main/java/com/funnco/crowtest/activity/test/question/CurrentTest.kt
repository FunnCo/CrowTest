package com.funnco.crowtest.activity.test.question

import com.funnco.crowtest.common.model.AnswerModel
import com.funnco.crowtest.common.model.question_models.*

class CurrentTest private constructor(){

    lateinit var listOfQuestions: List<BaseQuestion>

    fun answerAtQuestion(answer: AnswerModel?, numberOfQuestion: Int ) {
        when (listOfQuestions[numberOfQuestion].type) {
            "one_answer" -> {
                writeAnswer(answer!!, listOfQuestions[numberOfQuestion] as OneAnswerQuestion)
            }
            "multiple_answer" -> {
                writeAnswer(answer!!, listOfQuestions[numberOfQuestion] as MultipleAnswerQuestion)
            }
            "input_answer" -> {
                writeAnswer(answer, listOfQuestions[numberOfQuestion] as InputQuestion)
            }
        }
    }

    private fun writeAnswer(answer: AnswerModel, question: OneAnswerQuestion){
        question.answers.find { it.content == answer.content }!!.isSelected = true
        question.answers.find { it.content != answer.content }!!.isSelected = false
        question.isAnswered = true
    }

    private fun writeAnswer(answer: AnswerModel, question: MultipleAnswerQuestion){
        val answerModel = question.answers.find { it.content == answer.content }
        answerModel!!.isSelected = !answerModel.isSelected
        question.isAnswered = !question.answers.all { !it.isSelected }
    }

    private fun writeAnswer(answer: AnswerModel?, question: InputQuestion){
        question.answer = answer
        if(question.answer != null){
            question.isAnswered = true
        }
    }

    companion object{
        private var instance: CurrentTest? = null

        fun getInstanceOfTest(): CurrentTest{
            if(instance == null){
                instance = CurrentTest()
            }
            return instance!!
        }

        fun attachTestModel(test: List<BaseQuestion>) {
            getInstanceOfTest().listOfQuestions = test
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