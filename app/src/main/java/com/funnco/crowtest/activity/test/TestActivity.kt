package com.funnco.crowtest.activity.test

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.funnco.crowtest.activity.main.MainActivity
import com.funnco.crowtest.activity.test.question.CurrentTest
import com.funnco.crowtest.activity.test_result.TestResultActivity
import com.funnco.crowtest.databinding.ActivityTestBinding
import com.funnco.crowtest.repository.Repository
import java.text.SimpleDateFormat
import java.util.*

class TestActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        // Запрет скриншотов
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        binding.activityTestRcQuestionNumber.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        setContentView(binding.root)
        supportActionBar?.hide()

        Repository.loadQuestions("uuid1") { questions, test ->

            val countDown = object : CountDownTimer((test.timeForSolving * 60000).toLong(), 1000) {
                override fun onTick(p0: Long) {
                    val formatter = SimpleDateFormat("mm:ss")
                    binding.activityTestTxtTime.setText(formatter.format(Date(p0)))
                }

                override fun onFinish() {
                    finishTest(true)
                }
            }
            countDown.start()


            binding.activityBtnTestEnd.setOnClickListener {
                countDown.cancel()
                finishTest(false)
            }

            CurrentTest.attachTestModel(questions)

            val questionsAdapter = QuestionsAdapter(
                CurrentTest.getInstanceOfTest().listOfQuestions,
                supportFragmentManager,
                binding.activityTestRcQuestionNumber,
                lifecycle
            )

            CurrentTest.attachListener(object : CurrentTest.OnWriteListener{
                override fun onWrite() {
                    checkIfAllQuestionsAreAnswered(questionsAdapter)
                }

            })

            binding.activityTestVpQuestion.adapter = questionsAdapter
            binding.activityTestVpQuestion.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    questionsAdapter.currentSelectedPosition.position = position
                }
            })
            binding.activityTestRcQuestionNumber.adapter =
                QuestionNumberAdapter(questionsAdapter, binding.activityTestVpQuestion)
        }
    }

    fun checkIfAllQuestionsAreAnswered(adapter: QuestionsAdapter) {
        if (adapter.listOfQuestions.all { question ->
                question.isAnswered
            }) {
            binding.activityBtnTestEnd.isEnabled = true
        }
    }


    override fun onBackPressed() {
        AlertDialog.Builder(this).setTitle("Выход из теста")
            .setMessage("Вы уверены что хотите выйти? Данные будут потеряны!")
            .setNegativeButton("Нет", null)
            .setPositiveButton(
                "Да"
            ) { p0, p1 -> super.onBackPressed() }
            .show()
    }

    fun finishTest(isTimeExceeded: Boolean) {
        // TODO: Доделать отправку результатов теста, в том числе и в репозитории
        if (isTimeExceeded) {
            AlertDialog.Builder(this).setTitle("Завершение теста")
                .setMessage("К сожалению, время отведенное на выполнение теста вышло. Будут засчитаны только те ответы, которые вы успели ввести.")
                .setNeutralButton("ОК") { p0, p1 ->
                    startActivity(Intent(this@TestActivity, MainActivity::class.java))
                    finish()
                }.show()
        } else {
            startActivity(Intent(this, TestResultActivity::class.java))
            finish()
        }
    }
}