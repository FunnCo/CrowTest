package com.funnco.crowtest.activity.main.ui.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.funnco.crowtest.databinding.FragmentTestsBinding
import com.funnco.crowtest.repository.Repository

class TestsFragment : Fragment() {

    private lateinit var binding: FragmentTestsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTestsBinding.inflate(inflater, container, false)

        Repository.loadAvailableTests {
            binding.recyclerTests.adapter=TestsAdapter(it)
        }

        return binding.root
    }

}