package com.funnco.crowtest.activity.test.question

import com.funnco.crowtest.common.model.common.AnswerModelHolder
import com.funnco.crowtest.common.model.common.QuestionModel
import com.funnco.crowtest.common.model.response.TestInfoModel

class CurrentTest private constructor(){

    lateinit var listOfQuestions: List<QuestionModel>
    lateinit var listener: OnWriteListener
    lateinit var test: TestInfoModel

    fun answerAtQuestion(answer: AnswerModelHolder, questionPos: Int) {
        listOfQuestions[questionPos].body = answer
        listOfQuestions[questionPos].isAnswered = true
        listener.onWrite()
    }

    companion object{
        private var instance: CurrentTest? = null

        fun getInstanceOfTest(): CurrentTest{
            if(instance == null){
                instance = CurrentTest()
            }
            return instance!!
        }

        fun attachQuestions(test: List<QuestionModel>) {
            getInstanceOfTest().listOfQuestions = test
        }

        fun attachListener(newListener: OnWriteListener){
            getInstanceOfTest().listener = newListener
        }

        fun attachTestModel(currentTest: TestInfoModel){
            getInstanceOfTest().test = currentTest
        }

    }

    interface OnWriteListener{
        fun onWrite()
    }


}