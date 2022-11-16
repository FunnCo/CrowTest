package com.funnco.crowtest.activity.test_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}