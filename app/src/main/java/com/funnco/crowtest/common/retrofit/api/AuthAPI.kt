package com.funnco.crowtest.common.retrofit.api

import com.funnco.crowtest.common.model.TokenHolder
import com.funnco.crowtest.common.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*


interface AuthAPI {

    @POST("/user/register")
    fun registerUser(@Body user: UserModel): Call<Void>

    @GET("/user/login")
    fun login(@Query("mail") mail: String, @Query("password") password: String): Call<TokenHolder>

    @GET("/user/login/token")
    fun login(@Header("Authorization") token: String): Call<UserModel>


}