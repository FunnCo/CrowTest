package com.funnco.crowtest.activity.test

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.funnco.crowtest.databinding.ActivityTestBinding
import com.funnco.crowtest.repository.Repository

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

        Repository.loadQuestions("uuid1") {questions ->
            val questionsAdapter = QuestionsAdapter(
                questions,
                supportFragmentManager,
                binding.activityTestRcQuestionNumber,
                lifecycle
            )
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

    override fun onBackPressed() {
        AlertDialog.Builder(this).setTitle("Выход из теста")
            .setMessage("Вы уверены что хотите выйти? Данные будут потеряны!")
            .setNegativeButton("Нет", null)
            .setPositiveButton("Да"
            ) { p0, p1 -> super.onBackPressed()}
            .show()
    }
}