package com.funnco.crowtest.common.retrofit.api

import com.funnco.crowtest.common.model.request.SolvedTestModel
import com.funnco.crowtest.common.model.response.ResponseSolvedTestInfo
import com.funnco.crowtest.common.model.response.TestInfoModel
import retrofit2.Call
import retrofit2.http.*

interface TestAPI {

    @GET("/api/v2/stats/student/history")
    fun getFinished(@Header("Authorization") token: String): Call<List<ResponseSolvedTestInfo>>

    @GET("/api/v2/test/student/available")
    fun getAvailable(@Header("Authorization") token: String): Call<List<TestInfoModel>>

    @GET("/api/v2/test/student/{testId}")
    fun getTestInfo(
        @Header("Authorization") token: String,
        @Path("testId") testId: String
    ): Call<TestInfoModel>

    @GET("/api/v2/test/question/{testId}")
    fun getQuestions(
        @Header("Authorization") token: String,
        @Path("testId") testId: String
    ): Call<List<Any>>

    @POST("/api/v2/test/question/{testId}")
    fun postAnswersOnTest(
        @Header("Authorization") token: String,
        @Path("testId") testId: String,
        @Body answers: SolvedTestModel,
    ): Call<ResponseSolvedTestInfo>



}