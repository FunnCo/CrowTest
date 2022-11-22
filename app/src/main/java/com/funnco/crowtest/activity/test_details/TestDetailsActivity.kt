package com.funnco.crowtest.activity.test_details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.funnco.crowtest.activity.test.TestActivity
import com.funnco.crowtest.databinding.ActivityTestDetailsBinding

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

        binding.btnDetailsStart.setOnClickListener {
            // TODO: Передавать id теста
            startActivity(Intent(this, TestActivity::class.java))
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}