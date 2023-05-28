package com.funnco.crowtest.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.funnco.crowtest.common.model.request.UserLoginModel

object SharedPreferencesUtils {
    fun load(context: Context, key: String, value: Int){
        val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        prefs.edit().putInt(key, value).apply()
    }

    fun getInt(context: Context, key: String): Int{
        val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        return prefs.getInt(key, -1)
    }

    fun saveToken(context: Context, value: String){
        val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        prefs.edit().putString("token", "Bearer $value").apply()
    }

    fun getToken(context: Context): String?{
        val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        return prefs.getString("token", null)
    }

    fun removeToken(context: Context){
        val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        prefs.edit().remove("token").apply()
    }

    fun getLoginModel(context: Context): UserLoginModel{
        val prefs = context.getSharedPreferences("Credentials", Context.MODE_PRIVATE)
        val login = prefs.getString("login", null)
        val password = prefs.getString("password", null)
        return UserLoginModel(login, password)
    }

    fun saveLoginModel(context: Context, credentials: UserLoginModel){
        val prefs = context.getSharedPreferences("Credentials", Context.MODE_PRIVATE)
        prefs.edit().putString("login", credentials.email).apply()
        prefs.edit().putString("password", credentials.password).apply()
    }

    fun removeLoginModel(context: Context){
        val prefs = context.getSharedPreferences("Credentials", Context.MODE_PRIVATE)
        prefs.edit().remove("login").apply()
        prefs.edit().remove("password").apply()
    }

}