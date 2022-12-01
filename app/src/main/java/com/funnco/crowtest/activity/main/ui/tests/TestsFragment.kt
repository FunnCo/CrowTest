package com.funnco.crowtest.activity.main.ui.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.funnco.crowtest.common.model.TestModel
import com.funnco.crowtest.databinding.FragmentTestsBinding
import com.funnco.crowtest.repository.Repository

class TestsFragment : Fragment() {

    private lateinit var binding: FragmentTestsBinding
    private var listOfTests: List<TestModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTestsBinding.inflate(inflater, container, false)

        Repository.loadAvailableTests {
            listOfTests = it
            binding.recyclerTests.adapter = TestsAdapter(it)
        }

        binding.profileEtxtFilter.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank()) {
                binding.recyclerTests.adapter =
                    TestsAdapter(listOfTests.filter {
                        it.heading.lowercase().contains(text.toString().lowercase())
                    })
            } else {
                binding.recyclerTests.adapter =
                    TestsAdapter(listOfTests)
            }
        }

        return binding.root
    }

}