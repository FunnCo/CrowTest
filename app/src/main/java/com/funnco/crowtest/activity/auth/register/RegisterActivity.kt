package com.funnco.crowtest.activity.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.funnco.crowtest.R
import com.funnco.crowtest.activity.auth.login.LoginActivity
import com.funnco.crowtest.activity.main.MainActivity
import com.funnco.crowtest.common.model.UserModel
import com.funnco.crowtest.common.utils.SharedPreferencesUtils
import com.funnco.crowtest.databinding.ActivityRegisterBinding
import com.funnco.crowtest.repository.Repository

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.registerGrade.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listOf("8А","8Б","9A", "9Б","10А","10Б","11A", "11Б")))

        binding.registerSignUp.setOnClickListener {

            if(binding.registerEmail.text.toString().isBlank() ||
                binding.registerGrade.text.toString().isBlank() ||
                binding.registerName.text.toString().isBlank() ||
                binding.registerPassword.text.toString().isBlank() ||
                binding.registerPasswordRepeat.text.toString().isBlank()){
                showDiaolg("Ошибка", "Пожалуйста, заполните все поля")
                return@setOnClickListener
            }

            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()
            if(!(email.isNotBlank() && isMailValid(email))){
                showDiaolg("Ошибка", "Вы ввели некорректную почту")
                return@setOnClickListener
            }
            if(password.length < 6){
                showDiaolg("Ошибка", "Пароль должен состоять из 6 символов или более")
                return@setOnClickListener
            }
            if(password != binding.registerPasswordRepeat.text.toString()){
                showDiaolg("Ошибка", "Введенные пароли не слвпадают")
                return@setOnClickListener
            }
            if(binding.registerName.text.toString().trim().split(" ").size < 2){
                showDiaolg("Ошибка", "В поле ФИО должно быть как минимум 2 слова (фамилия и имя)")
                return@setOnClickListener
            }


            Repository.register(UserModel(binding.registerName.text.toString(), email, password, binding.registerGrade.text.toString())){ code ->
                if(code == 200){
                    Repository.login(email, password){ code, tokenHolder ->
                        SharedPreferencesUtils.saveToken(this, tokenHolder!!.token)
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                } else {
                    if(code == 401){
                        showDiaolg("Ошибка", "Пользователь с такой почтой уже зарегистрирован")
                    } else {
                        showDiaolg(
                            "Ошибка",
                            "Извините, произошла ошибка на сервере. Мы уже работаем над исправлением проблемы"
                        )
                    }
                    return@register
                }
            }

        }

        binding.registerBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun isMailValid(inputMail: String): Boolean {
        return inputMail.matches("^[a-z1-9.\\-_]+\\@[a-z1-9]+\\.[a-z]{2,3}$".toRegex())
    }

    private fun showDiaolg(heading :String, text: String){
        AlertDialog.Builder(this).setTitle(heading)
            .setMessage(text)
            .setNeutralButton("ОК", { _, _ -> })
            .show()
    }
}