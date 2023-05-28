package com.funnco.crowtest.common.retrofit.api

import com.funnco.crowtest.common.model.response.TokenHolder
import com.funnco.crowtest.common.model.request.UserLoginModel
import com.funnco.crowtest.common.model.request.UserRegisterModel
import com.funnco.crowtest.common.model.response.UserInfoModel
import retrofit2.Call
import retrofit2.http.*


interface AuthAPI {


    @POST("/api/v2/auth/register?role=STUDENT")
    fun registerUser(@Body user: UserRegisterModel): Call<TokenHolder>

    @POST("/api/v2/auth/login")
    fun login(@Body user: UserLoginModel): Call<TokenHolder>

    @GET("/api/v2/user/get")
    fun getInfo(@Header("Authorization") token: String): Call<UserInfoModel>

    @GET("/api/v2/user/get/{userId}")
    fun getInfoAboutSomeone(@Header("Authorization") token: String, @Path("userId") userId: String): Call<UserInfoModel>

}