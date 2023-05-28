package com.funnco.crowtest.activity.test.question

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.funnco.crowtest.R
import com.funnco.crowtest.common.model.common.QuestionModel
import com.funnco.crowtest.databinding.FragmentMultipleCorrectQuestionBinding
import com.google.android.material.checkbox.MaterialCheckBox


class MultipleCorrectQuestionFragment(private val question: QuestionModel, val pos: Int) : Fragment() {

    private lateinit var binding : FragmentMultipleCorrectQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMultipleCorrectQuestionBinding.bind(inflater.inflate(R.layout.fragment_multiple_correct_question, container, false))

        binding.fragmentQuestionTxtTask.text = question.description

        question.body.arrayAnswer!!.forEach { answer ->
            val answerButton = MaterialCheckBox(ContextThemeWrapper(requireContext(), R.style.MyDesignTextView))
            answerButton.text = answer.content
            answerButton.isChecked = answer.answer as Boolean
            answerButton.setOnCheckedChangeListener { compoundButton, b ->
                answer.answer = !(answer.answer as Boolean)
                CurrentTest.getInstanceOfTest().answerAtQuestion(question.body, pos)
            }
            binding.fragmentQuestionRgVariants.addView(answerButton)
        }

        return binding.root
    }

}