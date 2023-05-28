package com.funnco.crowtest.common.retrofit

import com.funnco.crowtest.common.retrofit.api.AuthAPI
import com.funnco.crowtest.common.retrofit.api.StatsAPI
import com.funnco.crowtest.common.retrofit.api.TestAPI
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    val BASE_URL = "http://mareev.maksimka.fvds.ru/"

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory
                .create(GsonBuilder().create()))
            .build()
    }

    val authAPI by lazy {
        retrofit.create(AuthAPI::class.java)
    }

    val testAPI by lazy {
        retrofit.create(TestAPI::class.java)
    }

    val statsAPI by lazy {
        retrofit.create(StatsAPI::class.java)
    }

}