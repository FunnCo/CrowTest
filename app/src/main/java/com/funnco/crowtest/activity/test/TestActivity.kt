package com.funnco.crowtest.activity.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.funnco.crowtest.activity.test.question.CurrentTest
import com.funnco.crowtest.activity.test_result.TestResultActivity
import com.funnco.crowtest.common.model.TestModel
import com.funnco.crowtest.databinding.ActivityTestBinding
import com.funnco.crowtest.repository.Repository
import java.text.SimpleDateFormat
import java.util.*

class TestActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding
    private var solveTime = 0f

    private lateinit var countDown : CountDownTimer

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

        val testId = intent.getStringExtra("test_id")

        Repository.loadQuestions(testId!!) { questions, test ->

            countDown = object : CountDownTimer((test.timeForSolving * 60000).toLong(), 1000) {
                override fun onTick(p0: Long) {
                    val formatter = SimpleDateFormat("mm:ss")
                    binding.activityTestTxtTime.setText(formatter.format(Date(p0)))
                    solveTime += 1000
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

            CurrentTest.attachQuestions(questions)
            CurrentTest.attachQuestions(test)

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
        val test = CurrentTest.getInstanceOfTest().test
        if (isTimeExceeded) {
            test.timeUsedToSolve = test.timeForSolving.toFloat()
            AlertDialog.Builder(this).setTitle("Завершение теста")
                .setMessage("К сожалению, время отведенное на выполнение теста вышло. Будут засчитаны только те ответы, которые вы успели ввести.")
                .show()
        } else {
            var isSureExit = false
            test.timeUsedToSolve = solveTime/60000f
            AlertDialog.Builder(this).setTitle("Завершение теста")
                .setMessage("Вы уверены, что хотите завершить тест досрочно?")
                .setPositiveButton("Да") { _, _ -> sendAnswers(test) }
                .setNegativeButton("Нет") { _, _ -> }
                .show()
        }
    }

    private fun sendAnswers(test: TestModel){
        val resultIntent = Intent(this, TestResultActivity::class.java)
        resultIntent.putExtra("test_id", test.id)
        countDown.cancel()
        Repository.sendAnswers(test.id, CurrentTest.getInstanceOfTest().listOfQuestions){
            startActivity(resultIntent)
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        countDown.cancel()
        finish()
    }
}