package com.funnco.crowtest.activity.main.ui.profile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.funnco.crowtest.databinding.FragmentStatsDialogBinding
import com.funnco.crowtest.repository.Repository

class StatsDialog : AppCompatDialogFragment() {

    lateinit var binding: FragmentStatsDialogBinding
    lateinit var dialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(activity)

        binding = FragmentStatsDialogBinding.inflate(layoutInflater)

        dialogBuilder.setView(binding.root)

        dialog = dialogBuilder.create()
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.btnStatsClose.setOnClickListener {
            dialog.cancel()
        }

        Repository.getStats {
            binding.pbStatsPlaceholder.visibility = View.GONE

            binding.txtStatsMark.text = String.format("%.2f", it.averageMark)
            binding.txtStatsTime.text = it.getPrettyTime()
            binding.txtStatsCorrectRatio.text = String.format("%.2f", it.correctAnswerRatio*100) + "%"
            binding.txtStatsTotal.text = it.totalTestsSolved.toString()

            binding.layoutStatsHolder.visibility = View.VISIBLE

        }

        return binding.root
    }

}