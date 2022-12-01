package com.funnco.crowtest.activity.main.ui.profile

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.funnco.crowtest.activity.auth.login.LoginActivity
import com.funnco.crowtest.common.utils.SharedPreferencesUtils
import com.funnco.crowtest.databinding.FragmentProfileBinding
import com.funnco.crowtest.repository.Repository

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val currentMode = SharedPreferencesUtils.getInt(requireContext(), "dark_mode")

        binding.btnProfileNightMode.setOnClickListener {
            SharedPreferencesUtils.load(requireContext(), "dark_mode", when (currentMode) {0 -> 1; else -> 0})
            if(currentMode == 0){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.btnProfileLogout.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Выход").setMessage("Вы уверены что хотите выйти из своего аккаунта?").setPositiveButton("Да"
            ) { p0, p1 ->
                startActivity(Intent(binding.root.context, LoginActivity::class.java))
                activity?.finish()
            }.setNegativeButton("Нет"){_,_ ->}.show()
        }

        Repository.loadDoneTests {
            binding.recyclerProfileFinishedTests.adapter = FinishedTestsAdapter(it)
        }

        return binding.root
    }

}