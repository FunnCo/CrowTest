package com.funnco.crowtest.common.utils

import android.content.Context
import android.content.SharedPreferences

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
        prefs.edit().putString("token", value).apply()
    }

    fun getToken(context: Context): String?{
        val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        return prefs.getString("token", null)
    }

    fun removeToken(context: Context){
        val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        prefs.edit().remove("token").apply()
    }
}