package com.funnco.crowtest.common.retrofit.api

import com.funnco.crowtest.common.model.response.StatsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface StatsAPI {
    @GET("/api/v2/stats/student/total")
    fun getStats(@Header("Authorization") token: String): Call<StatsModel>

}