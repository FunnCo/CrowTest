package com.funnco.crowtest.activity.main.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.funnco.crowtest.activity.auth.login.LoginActivity
import com.funnco.crowtest.activity.main.ui.common.FilterDialog
import com.funnco.crowtest.common.model.response.ResponseSolvedTestInfo
import com.funnco.crowtest.common.utils.SharedPreferencesUtils
import com.funnco.crowtest.databinding.FragmentProfileBinding
import com.funnco.crowtest.repository.Repository
import kotlin.streams.toList

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var listOfDoneTests: List<ResponseSolvedTestInfo> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        Repository.getUserInfo { code, user ->
            binding.txtProfileName.text =
                "${user!!.firstname} ${user!!.lastname} ${user!!.patronymic}"
            binding.txtProfileEmail.text = user!!.email
            binding.txtProfileGrade.text = user!!.groupName
        }


        val currentMode = SharedPreferencesUtils.getInt(requireContext(), "dark_mode")

        binding.btnProfileNightMode.setOnClickListener {
            SharedPreferencesUtils.load(
                requireContext(), "dark_mode", when (currentMode) {
                    0 -> 1; else -> 0
                }
            )
            if (currentMode == 0) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.btnProfileLogout.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Выход")
                .setMessage("Вы уверены что хотите выйти из своего аккаунта?").setPositiveButton(
                    "Да"
                ) { p0, p1 ->
                    SharedPreferencesUtils.removeToken(requireContext())
                    SharedPreferencesUtils.removeLoginModel(requireContext())
                    startActivity(Intent(binding.root.context, LoginActivity::class.java))
                    activity?.finish()
                }.setNegativeButton("Нет") { _, _ -> }.show()
        }


        binding.profileEtxtFilter.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank()) {
                val listToShow = listOfDoneTests.stream().filter { info ->
                    listOfDoneTests.find { it.testId == info.testId }?.title?.contains(text)
                        ?: false
                }.toList()

                binding.recyclerProfileFinishedTests.adapter = FinishedTestsAdapter(listToShow)
            } else {
                binding.recyclerProfileFinishedTests.adapter = FinishedTestsAdapter(listOfDoneTests)
            }
        }

        Repository.loadDoneTests {
            listOfDoneTests = it!!
            binding.recyclerProfileFinishedTests.adapter = FinishedTestsAdapter(it)
        }

        binding.btnProfileStats.setOnClickListener {
            StatsDialog().show(requireActivity().supportFragmentManager, "stats dialog")
        }

        binding.btnProfileFilter.setOnClickListener {
            Repository.parseDoneTestsInfo(listOfDoneTests) {
                FilterDialog(it, listOfDoneTests) { _, filteredResponseInfoModels ->
                    binding.recyclerProfileFinishedTests.adapter = FinishedTestsAdapter(filteredResponseInfoModels!!)
                }
                    .show(requireActivity().supportFragmentManager, "filter dialog")
            }
        }

        return binding.root
    }

}