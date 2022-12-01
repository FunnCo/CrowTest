package com.funnco.crowtest.activity.test_details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.funnco.crowtest.activity.test.TestActivity
import com.funnco.crowtest.databinding.ActivityTestDetailsBinding
import com.funnco.crowtest.repository.Repository

class TestDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityTestDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnDetailsBack.setOnClickListener {
            onBackPressed()
        }

        val testId = intent.getStringExtra("test_id")
        binding.btnDetailsStart.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            intent.putExtra("test_id",testId)
            startActivity(intent)
            finish()
        }


        Repository.getTestById(testId!!){
            binding.txtDetailsHeading.text = it.heading
            binding.testDetailsTxtTimeForSolving.text = "${it.timeForSolving} мин"
            binding.testDetailsTxtDescription.text = it.description
            binding.testDetailsTxtOpenDate.text = it.startDate
            binding.testDetailsTxtCloseDate.text = it.deadlineDate
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}