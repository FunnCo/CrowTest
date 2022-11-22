package com.funnco.crowtest.activity.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.funnco.crowtest.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}