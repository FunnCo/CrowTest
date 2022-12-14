package com.funnco.crowtest.activity.test.question

import com.funnco.crowtest.common.model.AnswerModel
import com.funnco.crowtest.common.model.TestModel
import com.funnco.crowtest.common.model.question_models.*

class CurrentTest private constructor(){

    lateinit var listOfQuestions: List<BaseQuestion>
    lateinit var listener: OnWriteListener
    lateinit var test: TestModel

    fun answerAtQuestion(answers: List<AnswerModel>, numberOfQuestion: Int ) {
        writeAnswer(answers, listOfQuestions[numberOfQuestion] as AccordanceQuestion)
        listener.onWrite()
    }

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
        listener.onWrite()
    }

    private fun writeAnswer(answers: List<AnswerModel>, question: AccordanceQuestion){
        question.isAnswered = true
        question.secondListOfAnswers = answers
        listener.onWrite()
    }

    private fun writeAnswer(answer: AnswerModel, question: MultipleAnswerQuestion){
        val answerModel = question.answers.find { it.content == answer.content }
        answerModel!!.isSelected = !answerModel.isSelected
        question.isAnswered = !question.answers.all { !it.isSelected }
        listener.onWrite()
    }

    private fun writeAnswer(answer: AnswerModel?, question: InputQuestion){
        question.answer = answer
        if(question.answer != null){
            question.isAnswered = true
            listener.onWrite()
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

        fun attachQuestions(test: List<BaseQuestion>) {
            getInstanceOfTest().listOfQuestions = test
        }

        fun attachListener(newListener: OnWriteListener){
            getInstanceOfTest().listener = newListener
        }

        fun attachQuestions(currentTest: TestModel){
            getInstanceOfTest().test = currentTest
        }

    }

    interface OnWriteListener{
        fun onWrite()
    }


    /** TODO: ?????????????????????? ??????????, ?????????? ???????????? ????????????.
     *
     *  TODO: ???????? ?????????????? ???????????? ?? ???????????????????????????? ?????? ?????????????? ???????????? ?????????? ?? ??????????
     *  TODO: ???????? ?????????????? ???????????????? ???????????? ?? ?????????????????????? ????????????
     */


}