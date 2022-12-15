package com.funnco.crowtest.activity.splash

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.funnco.crowtest.R
import com.funnco.crowtest.activity.auth.login.LoginActivity
import com.funnco.crowtest.activity.main.MainActivity
import com.funnco.crowtest.common.utils.SharedPreferencesUtils
import com.funnco.crowtest.common.utils.WindowUtils
import com.funnco.crowtest.repository.Repository


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val isDarkMode = SharedPreferencesUtils.getInt(baseContext, "dark_mode")
        when (isDarkMode) {
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> initLightMode()
        }

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val token = SharedPreferencesUtils.getToken(this)

            if (token != null) {
                Log.i("TAG", "c")
                Repository.login(token) { user ->
                    if (user != null) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
            } else {

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 1500)
    }


    private fun initLightMode() {
        val nightModeFlags: Int = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                WindowUtils.changeStatusBarColor(
                    ContextCompat.getColor(this, R.color.blue_dark),
                    window,
                    false
                )
                SharedPreferencesUtils.load(this, "dark_mode", 1)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }
            else -> {
                WindowUtils.changeStatusBarColor(
                    ContextCompat.getColor(this, R.color.smoke_white),
                    window,
                    true
                )
                SharedPreferencesUtils.load(this, "dark_mode", 0)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }
    }

}