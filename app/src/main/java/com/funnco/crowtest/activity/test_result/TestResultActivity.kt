package com.funnco.crowtest.activity.test_result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.funnco.crowtest.R
import com.funnco.crowtest.databinding.ActivityTestResultBinding

class TestResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}