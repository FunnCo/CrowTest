package com.funnco.crowtest.common.retrofit

import com.funnco.crowtest.common.retrofit.api.AuthAPI
import com.funnco.crowtest.common.retrofit.api.TestAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    val BASE_URL = "http://192.168.31.200:8080/"

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authAPI by lazy {
        retrofit.create(AuthAPI::class.java)
    }

    val testAPI by lazy {
        retrofit.create(TestAPI::class.java)
    }

}