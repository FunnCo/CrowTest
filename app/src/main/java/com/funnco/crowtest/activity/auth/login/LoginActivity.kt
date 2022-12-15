package com.funnco.crowtest.activity.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.funnco.crowtest.activity.auth.register.RegisterActivity
import com.funnco.crowtest.activity.main.MainActivity
import com.funnco.crowtest.common.utils.SharedPreferencesUtils
import com.funnco.crowtest.databinding.ActivityLoginBinding
import com.funnco.crowtest.repository.Repository

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.loginRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.loginSignIn.setOnClickListener {
            val email = binding.loginEmail.text?.toString()
            val password = binding.loginPassword.text?.toString()
            if (email.isNullOrBlank() || password.isNullOrBlank()) {
                AlertDialog.Builder(this).setTitle("Ошибка").setMessage("Не все поля заполнены")
                    .setNeutralButton("ОК", { _, _ -> })
                    .show()
                return@setOnClickListener
            }
            if (isMailValid(email!!)) {
                Repository.login(email, password!!) { code, token ->
                    if (code != 200) {
                        AlertDialog.Builder(this).setTitle("Ошибка")
                            .setMessage("Возможно вы ввели неправильные данные. Проверьте их, и попробуйте авториховаться снова")
                            .setNeutralButton("ОК", { _, _ -> })
                            .show()
                        return@login
                    }
                    SharedPreferencesUtils.saveToken(this, token!!.token)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            } else {
                AlertDialog.Builder(this).setTitle("Ошибка")
                    .setMessage("Возможно вы ввели неправильные данные. Проверьте их, и попробуйте авториховаться снова")
                    .setNeutralButton("ОК", { _, _ -> })
                    .show()
            }
        }
    }

    private fun isMailValid(inputMail: String): Boolean {
        return inputMail.matches("^[a-z1-9.\\-_]+\\@[a-z1-9]+\\.[a-z]{2,3}$".toRegex())
    }
}