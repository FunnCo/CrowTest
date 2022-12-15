package com.funnco.crowtest.common.retrofit.api

import com.funnco.crowtest.common.model.TestModel
import com.funnco.crowtest.common.model.question_models.BaseQuestion
import retrofit2.Call
import retrofit2.http.*

interface TestAPI {

    @GET("/test/get/finished")
    fun getFinished(@Header("Authorization") token: String): Call<List<TestModel>>

    @GET("/test/get/available")
    fun getAvailable(@Header("Authorization") token: String): Call<List<TestModel>>

    @GET("/test/get/questions")
    fun getQuestions(@Header("Authorization") token: String, @Query("testId") testId: String): Call<List<Any>>

    @POST("/test/post/answers")
    fun postAnswersOnTest(
        @Header("Authorization") token: String,
        @Header("testId") testId: String,
        @Header("solvingTime") solvingTime: Double,
        @Body answers: Any,

        ): Call<Void>

}