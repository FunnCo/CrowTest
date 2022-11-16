package com.funnco.crowtest.common.utils

import android.graphics.Color
import android.view.Window
import androidx.core.view.WindowCompat

object WindowUtils {
    fun changeStatusBarColor(color: Int, window: Window, isLight: Boolean = false){
        window.statusBarColor = color
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = isLight
    }
}