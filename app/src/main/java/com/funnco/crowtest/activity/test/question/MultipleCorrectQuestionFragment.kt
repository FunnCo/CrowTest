package com.funnco.crowtest.activity.test.question

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.funnco.crowtest.R
import com.funnco.crowtest.common.model.AnswerModel
import com.funnco.crowtest.common.model.question_models.MultipleAnswerQuestion
import com.funnco.crowtest.databinding.FragmentMultipleCorrectQuestionBinding
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.radiobutton.MaterialRadioButton


class MultipleCorrectQuestionFragment(private val question: MultipleAnswerQuestion, val pos: Int) : Fragment() {

    private lateinit var binding : FragmentMultipleCorrectQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMultipleCorrectQuestionBinding.bind(inflater.inflate(R.layout.fragment_multiple_correct_question, container, false))

        binding.fragmentQuestionTxtTask.text = question.task

        question.answers.forEach { answer ->
            val answerButton = MaterialCheckBox(ContextThemeWrapper(requireContext(), R.style.MyDesignTextView))
            answerButton.text = answer.content
            answerButton.isChecked = answer.isSelected
            answerButton.setOnCheckedChangeListener { compoundButton, b ->
                CurrentTest.getInstanceOfTest().answerAtQuestion(AnswerModel(answer.content), pos)
            }
            binding.fragmentQuestionRgVariants.addView(answerButton)
        }

        return binding.root
    }

}