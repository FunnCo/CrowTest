package com.funnco.crowtest.activity.main.ui.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.funnco.crowtest.activity.main.ui.common.FilterDialog
import com.funnco.crowtest.activity.main.ui.profile.FinishedTestsAdapter
import com.funnco.crowtest.common.model.response.TestInfoModel
import com.funnco.crowtest.databinding.FragmentTestsBinding
import com.funnco.crowtest.repository.Repository

class TestsFragment : Fragment() {

    private lateinit var binding: FragmentTestsBinding
    private var listOfTests: List<TestInfoModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTestsBinding.inflate(inflater, container, false)

        Repository.loadAvailableTests {
            listOfTests = it!!
            binding.recyclerTests.adapter = TestsAdapter(it)
        }


        binding.profileEtxtFilter.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank()) {
                binding.recyclerTests.adapter =
                    TestsAdapter(listOfTests.filter {
                        it.title!!.lowercase().contains(text.toString().lowercase())
                    })
            } else {
                binding.recyclerTests.adapter =
                    TestsAdapter(listOfTests)
            }
        }

        binding.btnProfileFilter.setOnClickListener {
            FilterDialog(listOfTests, null) { filteredTests, _ ->
                binding.recyclerTests.adapter = TestsAdapter(filteredTests)
            }
                .show(requireActivity().supportFragmentManager, "filter dialog")
        }

        return binding.root
    }

}