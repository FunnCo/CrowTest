package com.funnco.crowtest.activity.test_result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.funnco.crowtest.R
import com.funnco.crowtest.activity.main.MainActivity
import com.funnco.crowtest.databinding.ActivityTestResultBinding
import com.funnco.crowtest.repository.Repository

class TestResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val testId = intent.getStringExtra("test_id")
        Repository.getTestById(testId!!){
            binding.txtDetailsHeading.text = it.heading
            binding.testDetailsTxtTimeForSolving.text = "${it.timeUsedToSolve} мин"
            binding.testDetailsTxtDescription.text = it.description
            binding.testDetailsTxtCloseDate.text = it.solveDate
            binding.txtResultMark.text = it.mark

            binding.materialCardView4.setCardBackgroundColor(
                ContextCompat.getColor(
                    this,
                    when (it.mark!!.toInt()) {
                        5 -> R.color.excellent_mark
                        4 -> R.color.good_mark
                        3 -> R.color.satisfying_mark
                        else -> R.color.bad_mark
                    }
            ))
        }

        binding.btnDetailsStart.setOnClickListener {
            onBackPressed()
            finish()
        }
    }


}